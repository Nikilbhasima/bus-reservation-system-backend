package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.security.core.Authentication;

public interface UserInterface {
    public Users getUserById(Authentication authentication);
}
