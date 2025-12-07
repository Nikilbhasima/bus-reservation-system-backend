package com.sdcProject.busReservationSystem.controller.oauth2Controller;

import com.sdcProject.busReservationSystem.oauth2.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", oAuth2User.getEmail());
        userInfo.put("name", oAuth2User.getName());
        userInfo.put("picture", oAuth2User.getPicture());
        userInfo.put("authorities", oAuth2User.getAuthorities());

        return ResponseEntity.ok(userInfo);
    }
}
