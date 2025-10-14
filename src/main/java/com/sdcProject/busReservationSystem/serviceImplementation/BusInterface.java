package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Bus;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BusInterface {

    public Bus addBus(Bus bus, Authentication authentication);

    public Bus editBus(Bus bus, int bus_id);

    public List<Bus> findAllBuses(Authentication auth);
}
