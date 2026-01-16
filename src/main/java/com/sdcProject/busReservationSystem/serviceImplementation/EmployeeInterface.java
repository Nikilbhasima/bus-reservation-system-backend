package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.dto.OwnerDto;
import com.sdcProject.busReservationSystem.dto.SendNotification;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Driver;
import org.springframework.security.core.Authentication;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeInterface {

    Driver addDriverDetails(Driver driver, Authentication authentication);

    Driver editDriver(Driver driver,int driverId);

    List<Driver> getDriversByAgency(Authentication auth);

    Driver getDriverById(int driverId);

    List<Bookings> bookingsByDriverAndDate(Authentication authentication, LocalDate date);

    boolean sendNotificationToPassenger(int busId, LocalDate bookingDate, SendNotification sendNotification);

    List<Bookings> boardingNotification(int busId, LocalDate bookingDate,Authentication authentication);

    Driver getDriverData(Authentication authentication);

    Driver unassignDriver(int driverId);

    Driver assignDriver(int driverId, int busId);

    OwnerDto getOwner(int ownerId);

    List<OwnerDto> getAllOwners();

    void deleteOwner(int ownerId);
}
