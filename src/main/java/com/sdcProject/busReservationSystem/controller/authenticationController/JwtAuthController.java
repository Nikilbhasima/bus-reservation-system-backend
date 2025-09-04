package com.sdcProject.busReservationSystem.controller.authenticationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {

    @Autowired
    private JwtAuthService jwtAuthService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRegisterRequest authenticationRegisterRequest) {
        try {
            JwtAuthResponse response = jwtAuthService.register(authenticationRegisterRequest);

            Map<String, Object> success = new HashMap<>();
            success.put("message", "Registration successful");
            success.put("token", response.getToken());
            return ResponseEntity.ok(success);
        } catch (RuntimeException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(jwtAuthService.authenticate(authRequest));
    }
}
