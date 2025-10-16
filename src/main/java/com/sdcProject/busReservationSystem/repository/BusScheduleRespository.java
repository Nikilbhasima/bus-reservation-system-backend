package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.BusSchedules;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface BusScheduleRespository extends JpaRepository<BusSchedules,Integer> {
    List<BusSchedules> findByTravelAgency(TravelAgency travelAgency);
}
