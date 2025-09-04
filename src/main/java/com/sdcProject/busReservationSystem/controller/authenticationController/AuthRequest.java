package com.sdcProject.busReservationSystem.controller.authenticationController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String emailOrMobile ;
    private String password;
}
