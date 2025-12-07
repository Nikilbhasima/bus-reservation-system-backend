package com.sdcProject.busReservationSystem.controller.paymentController;

import com.sdcProject.busReservationSystem.dto.PaymentRequest;
import com.sdcProject.busReservationSystem.dto.PaymentResponse;
import com.sdcProject.busReservationSystem.service.EsewaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/payment")
@AllArgsConstructor
public class PaymentController {


    private EsewaService esewaService;

    @PostMapping("/initiate/{bookingId}")
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody PaymentRequest request, @PathVariable("bookingId") int bookingId) {
        PaymentResponse response = esewaService.initiatePayment(request,bookingId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/verify/{transactionUuid}")
    public ResponseEntity<Map<String, String>> verifyPayment(
            @PathVariable String transactionUuid) {

        boolean isVerified = esewaService.verifyPayment(transactionUuid);
        Map<String, String> response = new HashMap<>();

        if (isVerified) {
            response.put("status", "SUCCESS");
            response.put("message", "Payment verified successfully");
        } else {
            response.put("status", "FAILED");
            response.put("message", "Payment verification failed");
        }

        return ResponseEntity.ok(response);
    }
}
