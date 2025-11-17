package com.sdcProject.busReservationSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryDto {
    private long id;
    private String name;
    private String email;
    private String number;
    private String category;
    private String message;
    private String status;  // show to user
}

