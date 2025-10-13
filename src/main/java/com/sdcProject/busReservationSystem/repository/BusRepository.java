package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
}
