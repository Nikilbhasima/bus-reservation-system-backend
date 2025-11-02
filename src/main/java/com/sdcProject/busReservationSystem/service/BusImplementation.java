package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.enumFile.BusType;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.BusRepository;
import com.sdcProject.busReservationSystem.repository.TravelAgencyRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.BusInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusImplementation implements BusInterface {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TravelAgencyRepository travelAgencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Bus addBus(Bus bus, Authentication authentication) {

        Users user=userRepository.findByEmail(authentication.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency agency=travelAgencyRepository.findByUser(user);
        bus.setTravelAgency(agency);
        return busRepository.save(bus);
    }

    @Override
    public Bus editBus(Bus bus, int bus_id) {
        Bus bus1=busRepository.findById(bus_id).orElseThrow(()->new RuntimeException("Bus not found"));
        bus1.setBusType(bus.getBusType());
        bus1.setBusName(bus.getBusName());
        bus1.setBusRegistrationNumber(bus.getBusRegistrationNumber());
        bus1.setActive(bus.isActive());
        bus1.setTotalSeats(bus.getTotalSeats());
        bus1.setBusphotos(bus.getBusphotos());
        bus1.setActive(bus.isActive());
        bus1.setCurrentBusLocation(bus.getCurrentBusLocation());
        bus1.setIncreasedPrice(bus.getIncreasedPrice());

        return busRepository.save(bus1);
    }

    @Override
    public List<Bus> findAllBuses(Authentication auth) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Bus> buses=busRepository.findByTravelAgency(travelAgency);
        return buses;
    }

    @Override
    public Bus getBusById(int busId) {
        Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));
        return bus;
    }
}
