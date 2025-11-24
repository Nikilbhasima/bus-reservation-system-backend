package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Driver;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    List<Driver> findByTravelAgency(TravelAgency travelAgency);

    @Query(value = "SELECT * FROM driver WHERE driver_email = ?1", nativeQuery = true)
    Driver findByDriverEmail(String email);

}
