package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Routes;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface BusInterface {

    public Bus addBus(Bus bus, Authentication authentication);

    public Bus editBus(Bus bus, int bus_id);

    public List<Bus> findAllBuses(Authentication auth);

    public Bus getBusById(int busId);

    public List<Bus> getBusesByRoute(Routes routes, LocalDate travelDate);
}
