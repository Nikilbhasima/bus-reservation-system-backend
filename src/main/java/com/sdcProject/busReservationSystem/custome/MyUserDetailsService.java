package com.sdcProject.busReservationSystem.custome;


import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOp = userRepository.findByEmail(username);
        if(!userOp.isPresent()) {
            userOp=userRepository.findByPhoneNumber(username);
        }
        Users users = userOp.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new MyUserDetails(users);
    }

}
