package com.sdcProject.busReservationSystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdcProject.busReservationSystem.config.EsewaConfig;
import com.sdcProject.busReservationSystem.dto.PaymentRequest;
import com.sdcProject.busReservationSystem.dto.PaymentResponse;
import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.enumFile.PaymentStatus;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Transaction;
import com.sdcProject.busReservationSystem.repository.BookingRepository;
import com.sdcProject.busReservationSystem.repository.TransactionRespository;
import com.sdcProject.busReservationSystem.utils.HmacSignatureUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EsewaService {

    private TransactionRespository transactionRespository;
    private  EsewaConfig esewaConfig;
    private HmacSignatureUtil hmacSignatureUtil;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private BookingRepository bookingRepository;

    public PaymentResponse initiatePayment(PaymentRequest request, int bookingId){
        try{
            Bookings bookings = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // LOG THE INCOMING REQUEST
            log.info("Payment Request - totalAmount: {}, serviceCharge: {}",
                    request.getTotalAmount(), request.getServiceCharge());

            // Generate transaction id
            String transactionUuid = hmacSignatureUtil.generateTransactionUuid();

            // Calculate amounts correctly
            double serviceCharge = request.getServiceCharge() != 0 ? request.getServiceCharge() : 0.0;
            double taxAmount = 0.0;
            double deliveryCharge = 0.0;

            // Base product amount
            double productAmount = request.getTotalAmount();

            // Total = product + service + tax + delivery
            double totalAmount = productAmount + serviceCharge + taxAmount + deliveryCharge;

            // LOG CALCULATED VALUES
            log.info("Calculated - productAmount: {}, totalAmount: {}, serviceCharge: {}",
                    productAmount, totalAmount, serviceCharge);

            String totalAmountStr = String.format("%.2f", totalAmount);
            String productAmountStr = String.format("%.2f", productAmount);

            // LOG FORMATTED STRINGS
            log.info("Formatted - amount: {}, total_amount: {}",
                    productAmountStr, totalAmountStr);

            // Generate signature using TOTAL amount
            String signatureString = hmacSignatureUtil.generateSignatureString(
                    totalAmountStr, transactionUuid, request.getProductCode());
            String signature = hmacSignatureUtil.generateHmacSha256Signature(
                    signatureString, esewaConfig.getSecretKey());

            Transaction transaction = new Transaction();
            transaction.setTransactionUuid(transactionUuid);
            transaction.setBookings(bookings);
            transaction.setProductCode(request.getProductCode());
            transaction.setProductName(request.getProductName());
            transaction.setTotalAmount(totalAmount);
            transaction.setServiceCharge(serviceCharge);
            transaction.setStatus("PENDING");
            transaction.setCustomerEmail(request.getCustomerEmail());
            transaction.setSignature(signature);
            transaction.setCreatedAt(LocalDateTime.now());

            transactionRespository.save(transaction);

            Map<String, String> formData = new HashMap<>();
            formData.put("amount", productAmountStr);
            formData.put("total_amount", totalAmountStr);
            formData.put("tax_amount", String.format("%.2f", taxAmount));
            formData.put("product_delivery_charge", String.format("%.2f", deliveryCharge));
            formData.put("transaction_uuid", transactionUuid);
            formData.put("product_code", request.getProductCode());
            formData.put("product_service_charge", String.format("%.2f", serviceCharge));
            formData.put("success_url", esewaConfig.getSuccessUrl());
            formData.put("failure_url", esewaConfig.getFailureUrl());
            formData.put("signed_field_names", "total_amount,transaction_uuid,product_code");
            formData.put("signature", signature);

            // LOG FINAL FORM DATA
            log.info("Final formData: {}", formData);

            return new PaymentResponse(
                    esewaConfig.getPaymentUrl(),
                    transactionUuid,
                    signature,
                    "SUCCESS",
                    "Payment form data generated successfully",
                    formData
            );
        } catch (Exception e) {
            log.error("Error initiating payment: ", e);
            return new PaymentResponse(null, null, null, "ERROR",
                    "Failed to initiate payment", null);
        }
    }


    public boolean verifyPayment(String transactionUuid) {
        try {
            log.info("Verifying payment - transactionUuid: {}", transactionUuid);

            Optional<Transaction> transactionOpt = transactionRespository.findByTransactionUuid(transactionUuid);

            if (!transactionOpt.isPresent()) {
                log.error("Transaction not found for UUID: {}", transactionUuid);
                return false;
            }

            Transaction transaction = transactionOpt.get();
            Bookings bookings = transaction.getBookings();

//i think you have to added payment type, you haven't create payment type and enum file
            bookings.setPaymentStatus(PaymentStatus.PAID);
            bookings.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(bookings);

            // Build correct verification URL
            String verificationUrl = esewaConfig.getVerificationUrl() +
                    "?product_code=" + esewaConfig.getMerchantCode() +
                    "&total_amount=" + transaction.getTotalAmount() +
                    "&transaction_uuid=" + transactionUuid;

            log.info("Verification URL: {}", verificationUrl);

            HttpGet request = new HttpGet(verificationUrl);
            request.setHeader("Accept", "application/json");

            CloseableHttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            log.info("eSewa verification response: {}", responseBody);

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(responseBody);

            String status = jsonResponse.get("status").asText();

            if ("COMPLETE".equalsIgnoreCase(status)) {
                // Update transaction status
                transaction.setStatus("SUCCESS");
                if (jsonResponse.has("refId")) {
                    transaction.setEsewaTransactionCode(jsonResponse.get("refId").asText());
                }
                transactionRespository.save(transaction);
                return true;
            }
            return false;

        } catch (Exception e) {
            log.error("Error verifying payment: ", e);
            return false;
        }
    }



}
