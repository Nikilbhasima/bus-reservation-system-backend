package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.dto.SendNotification;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Driver;
import org.springframework.security.core.Authentication;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeInterface {

    public Driver addDriverDetails(Driver driver, Authentication authentication);

    public Driver editDriver(Driver driver,int driverId);

    public List<Driver> getDriversByAgency(Authentication auth);

    public Driver getDriverById(int driverId);

    public List<Bookings> bookingsByDriverAndDate(Authentication authentication, LocalDate date);

    public boolean sendNotificationToPassenger(int busId, LocalDate bookingDate, SendNotification sendNotification);

    public boolean boardingNotification(int busId, LocalDate bookingDate);

    public Driver getDriverData(Authentication authentication);

    public Driver unassignDriver(int driverId);
    public Driver assignDriver(int driverId, int busId);
}
