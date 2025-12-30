package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Routes;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface BusInterface {

    Bus addBus(Bus bus, Authentication authentication);

    Bus editBus(Bus bus, int bus_id);

    List<Bus> findAllBuses(Authentication auth);

    Bus getBusById(int busId, LocalDate date);

    Bus getBusById(int busId);

    List<Bus> getBusesByRoute(Routes routes, LocalDate travelDate);

    Bus changeBusLocation(int busId);

    Bus updateBusStatus(int busId);

    Integer countBuses();
}
