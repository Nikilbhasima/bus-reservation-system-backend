package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.BusSchedules;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.BusScheduleRespository;
import com.sdcProject.busReservationSystem.repository.TravelAgencyRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.BusScheduleInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class BusScheduleImplementation implements BusScheduleInterface {


    private UserRepository userRepository;
    private TravelAgencyRepository travelAgencyRepository;
    private BusScheduleRespository busScheduleRespository;

    @Override
    public BusSchedules addBusSchedules(BusSchedules busSchedules, Authentication auth) {
        Users user=userRepository.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(user);
        busSchedules.setTravelAgency(travelAgency);
        return busScheduleRespository.save(busSchedules);
    }

    @Override
    public BusSchedules editBusSchedules(BusSchedules busSchedules, int busScheduleId) {
        BusSchedules busSchedules1=busScheduleRespository.findById(busScheduleId).orElseThrow(() -> new RuntimeException("BusSchedule not found"));
        busSchedules1.setDepartureTime(busSchedules.getDepartureTime());
        busSchedules1.setPeriod(busSchedules.getPeriod());

        return busScheduleRespository.save(busSchedules1);
    }

    @Override
    public List<BusSchedules> getAllBusSchedules(Authentication auth) {
        Users user=userRepository.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(user);
        return busScheduleRespository.findByTravelAgency(travelAgency);
    }
}
