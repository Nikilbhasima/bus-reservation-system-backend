package com.sdcProject.busReservationSystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Component
@Slf4j
public class HmacSignatureUtil {
    public String generateHmacSha256Signature(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (Exception e) {
            log.error("Error generating HMAC-SHA256 signature: ", e);
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    public String generateTransactionUuid() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }

    public String generateSignatureString(String totalAmount, String transactionUuid, String productCode) {
        return String.format("total_amount=%s,transaction_uuid=%s,product_code=%s",
                totalAmount, transactionUuid, productCode);
    }
}
