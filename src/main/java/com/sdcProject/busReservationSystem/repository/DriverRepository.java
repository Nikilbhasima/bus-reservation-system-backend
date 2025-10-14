package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Driver;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    List<Driver> findByTravelAgency(TravelAgency travelAgency);
}
