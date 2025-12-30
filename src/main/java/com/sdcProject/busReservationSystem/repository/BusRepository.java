package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
    List<Bus> findByTravelAgency(TravelAgency travelAgency);

    List<Bus> findByRoutes(Routes routes);

    @Query(value = "SELECT COUNT(*) FROM bus",nativeQuery = true)
    Integer totalBus();

}
