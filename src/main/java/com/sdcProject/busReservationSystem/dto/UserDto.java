package com.sdcProject.busReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserDto {
    private int userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String password;
    private String image;

}
