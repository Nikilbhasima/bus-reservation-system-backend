package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.modal.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String image;


    public UserDto(Users user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.image = user.getImage();

    }

}
