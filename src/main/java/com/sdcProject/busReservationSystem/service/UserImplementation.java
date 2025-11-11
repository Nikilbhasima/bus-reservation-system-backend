package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserImplementation implements UserInterface {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users getUserById(Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        return user;
    }

    @Override
    public Users updateUser(Users user, Authentication authentication) {
        Users users=userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        users.setImage(user.getImage());
        users.setAddress(user.getAddress());
        users.setEmail(user.getEmail());
        users.setUsername(user.getUsername());
        users.setPhoneNumber(user.getPhoneNumber());
        users.setGender(user.getGender());
        if(user.getPassword()!=null){
            users.setPassword( passwordEncoder.encode(user.getPassword()) );

        }
        return userRepository.save(users);
    }


}
