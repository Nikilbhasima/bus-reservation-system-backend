package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.dto.OwnerDto;
import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.security.core.Authentication;

public interface UserInterface {
    Users getUserById(Authentication authentication);

    Users updateUser(Users user, Authentication authentication);

    void updateOwnerData(int ownerId, OwnerDto ownerDto);
}
