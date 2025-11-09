package com.sdcProject.busReservationSystem.controller;

import com.sdcProject.busReservationSystem.dto.UserDto;
import com.sdcProject.busReservationSystem.service.UserImplementation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
