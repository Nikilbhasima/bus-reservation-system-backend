package com.sdcProject.busReservationSystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class EsewaConfig {

    private String merchantCode;

    private String successUrl;

    private String failureUrl;

    private String paymentUrl;

    private String verificationUrl;

    private String secretKey;

    public EsewaConfig(@Value("${esewa.merchant.code}") String merchantCode,
                       @Value("${esewa.success.url}") String successUrl,
                       @Value("${esewa.failure.url}") String failureUrl,
                       @Value("${esewa.payment.url}") String paymentUrl,
                       @Value("${esewa.verification.url}") String verificationUrl,
                       @Value("${esewa.secret.key}") String secretKey) {
        this.merchantCode = merchantCode;
        this.successUrl = successUrl;
        this.failureUrl = failureUrl;
        this.paymentUrl = paymentUrl;
        this.verificationUrl = verificationUrl;
        this.secretKey = secretKey;
    }
}
