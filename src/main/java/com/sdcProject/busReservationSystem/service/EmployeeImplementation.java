package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.dto.SendNotification;
import com.sdcProject.busReservationSystem.enumFile.AssignStatus;
import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.modal.*;
import com.sdcProject.busReservationSystem.repository.*;
import com.sdcProject.busReservationSystem.serviceImplementation.EmployeeInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeImplementation implements EmployeeInterface {

    private DriverRepository driverRepository;
    private UserRepository userRepository;
    private TravelAgencyRepository travelAgencyRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private BusRepository busRepository;
    private BookingRepository bookingRepository;
    private MailService mailService;



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

        if(driver.getBus()!=null && driver.getBus().getBusId()!=0){
            Bus bus=busRepository.findById(driver.getBus().getBusId()).orElseThrow(() -> new RuntimeException("Bus not found"));
            driver.setBus(bus);
            bus.setAssignStatus(AssignStatus.ASSIGN);
            busRepository.save(bus);
        }

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
        if (driver.getBus() != null && driver.getBus().getBusId()!=0) {
            Bus oldBus=busRepository.findById(driver1.getBus().getBusId()).orElseThrow(() -> new RuntimeException("Bus not found"));
            oldBus.setAssignStatus(AssignStatus.UNASSIGN);
            Bus bus=busRepository.findById(driver.getBus().getBusId()).orElseThrow(() -> new RuntimeException("Bus not found"));
            bus.setAssignStatus(AssignStatus.ASSIGN);
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
        return driverRepository.findByTravelAgency(travelAgency);
    }

    @Override
    public Driver getDriverById(int driverId) {
               return driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    @Override
    public List<Bookings> bookingsByDriverAndDate(Authentication authentication, LocalDate date) {
        Driver driver=driverRepository.findByDriverEmail(authentication.getName());
        return bookingRepository.findBookingsByBusIdAndTripDate(driver.getBus().getBusId(), date);
    }

//    send notification to unboard passenger
    public boolean sendNotificationToPassenger(int busId, LocalDate bookingDate, SendNotification sendNotification) {
        List<Bookings> bookingsList=bookingRepository.findBookingsByBusIdAndTripDate(busId,bookingDate);
        List<String> listOfEmails = bookingsList.stream()
                .filter(data->!data.isBoard())
                .map(b -> b.getUser().getEmail())
                .toList();
        mailService.sendNotification(listOfEmails,sendNotification);
        return true;
    }

    @Override
    public List<Bookings> boardingNotification(int busId, LocalDate bookingDate,Authentication authentication) {

        log.info("Boarding notification"+busId+" "+bookingDate);
        Driver driver=driverRepository.findByDriverEmail(authentication.getName());

        List<Bookings> bookingsList =
                bookingRepository.findBookingsByBusIdAndTripDate(driver.getBus().getBusId(), bookingDate);

        bookingsList.forEach((data)->log.info(data.getUser().getEmail()));

        List<Bookings> activeBookings = bookingsList.stream()
                .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                .toList();

        List<String> listOfEmails = activeBookings.stream()
                .map(b -> b.getUser().getEmail())
                .toList();

        mailService.boardingNotification(listOfEmails);

        activeBookings.forEach(b -> b.setJourneyStarted(true));


        return bookingRepository.saveAll(activeBookings);
    }

    @Override
    public Driver getDriverData(Authentication authentication) {
        return driverRepository.findByDriverEmail(authentication.getName());
    }

    @Override
    public Driver unassignDriver(int driverId) {
        Driver driver=driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
        Bus bus=driver.getBus();
        bus.setAssignStatus(AssignStatus.UNASSIGN);
        driver.setBus(null);
        busRepository.save(bus);
        return driverRepository.save(driver);
    }

    @Override
    public Driver assignDriver(int driverId, int busId) {
        Bus bus=busRepository.findById(busId).orElseThrow(() -> new RuntimeException("Bus not found"));
        Driver driver=driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setBus(bus);
        driver.setDriver_email(driver.getDriver_email());
        bus.setAssignStatus(AssignStatus.ASSIGN);
        busRepository.save(bus);
        return driverRepository.save(driver);
    }
}
