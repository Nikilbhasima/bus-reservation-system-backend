package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
