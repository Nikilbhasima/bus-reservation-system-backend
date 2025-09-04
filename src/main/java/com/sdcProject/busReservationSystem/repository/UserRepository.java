package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
}
