package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
public interface RoutesRepository extends CrudRepository<Routes, Integer> {
    List<Routes> findByTravelAgency(TravelAgency travelAgency);
}
