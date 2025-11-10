package com.sdcProject.busReservationSystem.controller;

import com.sdcProject.busReservationSystem.dto.UserDto;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.service.UserImplementation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UsersController {
    @Autowired
    private UserImplementation userImplementation;

    @GetMapping("/getUserById")
    public ResponseEntity<UserDto> getUserById(Authentication authentication) {
        UserDto userDto=new UserDto(userImplementation.getUserById(authentication));
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PostMapping("/updateUserDetails")
    public ResponseEntity<UserDto> updateUserDetails(Authentication authentication, @RequestBody Users user) {
        UserDto userDto=new UserDto(userImplementation.updateUser(user,authentication));
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
