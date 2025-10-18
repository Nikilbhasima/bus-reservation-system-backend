package com.sdcProject.busReservationSystem.serviceImplementation;


import com.sdcProject.busReservationSystem.modal.BusSchedules;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BusScheduleInterface {

    public BusSchedules addBusSchedules(BusSchedules busSchedules, Authentication auth);

    public BusSchedules editBusSchedules(BusSchedules busSchedules, int busScheduleId);

    public List<BusSchedules> getAllBusSchedules(Authentication auth);
}
