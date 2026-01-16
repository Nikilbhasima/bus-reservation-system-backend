package com.sdcProject.busReservationSystem.controller.authenticationController;

import com.sdcProject.busReservationSystem.custome.MyUserDetailsService;
import com.sdcProject.busReservationSystem.dto.UserDto;
import com.sdcProject.busReservationSystem.jwtConfig.JwtService;
import com.sdcProject.busReservationSystem.modal.Roles;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.RoleRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleRepository roleRepository;

@Autowired
private AuthenticationManager authenticationManager;

    public JwtAuthResponse register(AuthenticationRegisterRequest authRequest) {
        if (userRepository.findByEmail(authRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }
        if (userRepository.findByPhoneNumber(authRequest.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number is already registered");
        }

        Users user =new Users();
        user.setUsername(authRequest.getUsername());
        user.setEmail(authRequest.getEmail());
        user.setPhoneNumber(authRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        if(authRequest.getImage()!=null){
            user.setImage(authRequest.getImage());
        }

        List<Roles> roles = new ArrayList<>();
        Roles role;

        List<Users> users = userRepository.findAll();

        if(users.isEmpty()){
            List<Roles> role1=roleRepository.findAll();
            roles.addAll(role1);
        }else{
            if(authRequest.getRole().equals("ROLE_OWNER")){
                System.out.println(authRequest.getRole());
                Optional<Roles> roleExists = roleRepository.findByRole("ROLE_OWNER");
                if(roleExists.isPresent()) {
                    role = roleExists.get();
                    roles.add(role);
                }
            }
            if(authRequest.getRole().equals("ROLE_USER")){
                System.out.println(authRequest.getRole());
                Optional<Roles> roleExists = roleRepository.findByRole("ROLE_USER");
                if(roleExists.isPresent()) {
                    role = roleExists.get();
                    roles.add(role);
                }
            }

            if(authRequest.getRole().equals("ROLE_BUS")){
                System.out.println(authRequest.getRole());
                Optional<Roles> roleExists = roleRepository.findByRole("ROLE_BUS");
                if(roleExists.isPresent()) {
                    role = roleExists.get();
                    roles.add(role);
                }
            }



        }

        user.setRoles(roles);
        userRepository.save(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getPhoneNumber());
        String token=jwtService.generateToken(userDetails);

        return new JwtAuthResponse(token);

    }

    public JwtAuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailOrMobile(), request.getPassword()));
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getEmailOrMobile());

        String token = jwtService.generateToken(userDetails);
        return new JwtAuthResponse(token);
    }
}
