package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String username);

    Optional<Users> findByPhoneNumber(String username);
}
