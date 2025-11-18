package com.sdcProject.busReservationSystem.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class OtpEntryModal {

    private final String otp;

    private final LocalDateTime expiryDate;

    public OtpEntryModal(String otp, LocalDateTime expiryDate) {
        this.otp = otp;
        this.expiryDate = expiryDate;
    }




}
