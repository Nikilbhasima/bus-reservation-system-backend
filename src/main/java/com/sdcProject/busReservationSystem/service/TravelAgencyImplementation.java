package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.dto.AdminDashboardDTO;
import com.sdcProject.busReservationSystem.dto.TravelAgencyDTO;
import com.sdcProject.busReservationSystem.modal.*;
import com.sdcProject.busReservationSystem.repository.TravelAgencyRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.TravelAgencyInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TravelAgencyImplementation implements TravelAgencyInterface {

    private TravelAgencyRepository travelAgencyRepository;
    private UserRepository userRepository;
    private BusImplementation busImplementation;
    private EmployeeImplementation employeeImplementation;
    private BookingImplementation bookingImplementation;

    @Override
    public void addTravelAgency(TravelAgency travelAgency, Authentication authentication) {
        Optional<Users> user = userRepository.findByEmail(authentication.getName());
        if (user.isPresent()) {
            travelAgency.setUser(user.get());
        }

        travelAgencyRepository.save(travelAgency);

    }

    @Override
    public TravelAgencyDTO editTravelAgency(TravelAgency travelAgency, Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelAgency travelAgency1=travelAgencyRepository.findByUser(user);

        if (travelAgency1 == null) {
            throw new RuntimeException("Travel agency not found for this user");
        }
        travelAgency1.setTravel_agency_name(travelAgency.getTravel_agency_name());
        travelAgency1.setRegistration_number(travelAgency.getRegistration_number());
        travelAgency1.setAddress(travelAgency.getAddress());
        travelAgency1.setAgency_logo(travelAgency.getAgency_logo());
        return new TravelAgencyDTO(travelAgencyRepository.save(travelAgency1));
    }

    @Override
    public TravelAgency getTravelAgency(Authentication authentication) {
        Users user=userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return travelAgencyRepository.findByUser(user);
    }

    @Override
    public AdminDashboardDTO getAdminDashboardData(Authentication authentication) {
        Users user=userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(user);
        List<Bus> busList=busImplementation.findAllBuses(authentication);
        List<Driver> driverList=employeeImplementation.getDriversByAgency(authentication);
        List<Bookings> bookingsList=bookingImplementation.getActiveBookingsOfAgency(travelAgency);
        float revenue= bookingImplementation.calculateTotalRevenue(travelAgency);
        Map<String,Integer> pie=bookingImplementation.dataForPie(travelAgency);

        return AdminDashboardDTO.builder()
                .totalDrivers(driverList.size())
                .totalBuses(busList.size())
                .activeBookings(bookingsList.size())
                .totalRevenue(revenue)
                .pieData(pie)
                .build();
    }
}
