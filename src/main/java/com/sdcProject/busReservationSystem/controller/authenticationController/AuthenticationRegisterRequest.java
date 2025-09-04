package com.sdcProject.busReservationSystem.controller.authenticationController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRegisterRequest {
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private String password;
}
