package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.Driver;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.DriverRepository;
import com.sdcProject.busReservationSystem.repository.TravelAgencyRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeImplementation implements EmployeeInterface {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelAgencyRepository travelAgencyRepository;

    @Override
    public Driver addDriverDetails(Driver driver, Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(user);
        driver.setTravelAgency(travelAgency);
        return driverRepository.save(driver);
    }

    @Override
    public Driver editDriver(Driver driver, int driverId) {
        Driver driver1=driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));

        driver1.setDriver_name(driver.getDriver_name());
        driver1.setDriver_address(driver.getDriver_address());
        driver1.setDriver_email(driver.getDriver_email());
        driver1.setDriver_phone(driver.getDriver_phone());
        driver1.setDriver_license_number(driver.getDriver_license_number());
        driver1.setLicense_photo(driver.getLicense_photo());
        driver1.setBus(driver.getBus());
        driver1.setTravelAgency(driver.getTravelAgency());
        if (driver.getBus() != null) {
            driver1.setBus(driver.getBus());
        }


        return driverRepository.save(driver1);
    }

    @Override
    public List<Driver> getDriversByAgency(Authentication auth) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Driver> drivers=driverRepository.findByTravelAgency(travelAgency);
        return drivers;
    }
}
