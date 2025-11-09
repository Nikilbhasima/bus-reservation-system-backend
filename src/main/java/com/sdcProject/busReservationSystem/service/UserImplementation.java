package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserImplementation implements UserInterface {


    @Autowired
    private UserRepository userRepository;

    @Override
    public Users getUserById(Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        return user;
    }
}
