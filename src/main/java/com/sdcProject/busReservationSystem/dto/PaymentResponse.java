package com.sdcProject.busReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String paymentUrl;
    private String transactionUuid;
    private String signature;
    private String status;
    private String message;
    private Map<String, String> formData;
}
