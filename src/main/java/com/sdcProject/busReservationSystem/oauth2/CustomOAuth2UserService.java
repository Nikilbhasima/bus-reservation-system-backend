package com.sdcProject.busReservationSystem.oauth2;

import com.sdcProject.busReservationSystem.modal.Roles;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.RoleRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String name = oidcUser.getGivenName() + " " + (oidcUser.getFamilyName() != null ? oidcUser.getFamilyName() : "");

        // Find or create user
        Users user = findOrCreateUser(email, name, oidcUser.getPicture(), String.valueOf(oidcUser.getAddress()),oidcUser.getGender(),oidcUser.getPhoneNumber());

        // Convert user roles to authorities
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

        return new CustomOAuth2User(oidcUser, authorities);
    }

    private Users findOrCreateUser(String email, String name, String picture,String address, String gender, String phoneNumber) {
        Optional<Users> existingUser = userRepository.findByEmail(email);
        System.out.println(gender+":"+phoneNumber);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // Create new user
        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setUsername(name);
        newUser.setImage(picture);
        newUser.setAddress(null);
        newUser.setGender(null);
        newUser.setPhoneNumber(null);

        // Set default role for OAuth2 users (ROLE_USER)
        List<Roles> roles = new ArrayList<>();
        Optional<Roles> userRole = roleRepository.findByRole("ROLE_USER");
        if (userRole.isPresent()) {
            roles.add(userRole.get());
        } else {
            // Create default role if it doesn't exist
            Roles defaultRole = new Roles();
            defaultRole.setRole("ROLE_USER");
            roleRepository.save(defaultRole);
            roles.add(defaultRole);
        }

        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }
}