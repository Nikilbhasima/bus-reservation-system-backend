package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.*;
import com.sdcProject.busReservationSystem.repository.*;
import com.sdcProject.busReservationSystem.serviceImplementation.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BusRepository busRepository;

    @Override
    public Driver addDriverDetails(Driver driver, Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(user);
        driver.setTravelAgency(travelAgency);

        Users user2=new Users();
        user2.setEmail(driver.getDriver_email());
        user2.setPhoneNumber(driver.getDriver_phone());
        user2.setPassword(passwordEncoder.encode("12345678"));
        Roles role=roleRepository.findByRole("ROLE_BUS").orElseThrow(() -> new RuntimeException("Role not found"));
        List<Roles> roles=new ArrayList<>();
        roles.add(role);
        user2.setRoles(roles);
        userRepository.save(user2);

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
            Bus bus=busRepository.findById(driver.getBus().getBusId()).orElseThrow(()->new RuntimeException("Bus Not Found"));
            driver1.setBus(bus);
        }

        Users users=userRepository.findByEmail(driver.getDriver_email()).orElseThrow(() -> new RuntimeException("User not found"));
        users.setUsername(driver.getDriver_name());
        users.setPhoneNumber(driver.getDriver_phone());
        users.setEmail(driver.getDriver_email());
        users.setAddress(driver.getDriver_address());
        users.setImage(driver.getDriver_photo());
        userRepository.save(users);


        return driverRepository.save(driver1);
    }

    @Override
    public List<Driver> getDriversByAgency(Authentication auth) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Driver> drivers=driverRepository.findByTravelAgency(travelAgency);
        return drivers;
    }

    @Override
    public Driver getDriverById(int driverId) {
        Driver driver=driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));

        return driver;
    }
}
