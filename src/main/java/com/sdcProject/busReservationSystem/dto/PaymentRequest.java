package com.sdcProject.busReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String productCode;
    private String productName;
    private double totalAmount;
    private double serviceCharge;
    private String customerEmail;
    private String customerPhone;
}
