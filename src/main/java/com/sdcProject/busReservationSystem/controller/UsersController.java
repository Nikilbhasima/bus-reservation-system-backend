package com.sdcProject.busReservationSystem.controller;

import com.sdcProject.busReservationSystem.dto.OwnerDto;
import com.sdcProject.busReservationSystem.dto.UserDto;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.service.UserImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UsersController {

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

    @PutMapping("/updateOwner/{ownerId}")
    public void updateOwnerDetails(@PathVariable int ownerId, @RequestBody OwnerDto ownerDto) {
        userImplementation.updateOwnerData(ownerId,ownerDto);
    }
}
