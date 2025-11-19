package com.sdcProject.busReservationSystem.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtpResponseModal {
    private boolean success;

    private String message;


    public OtpResponseModal(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
