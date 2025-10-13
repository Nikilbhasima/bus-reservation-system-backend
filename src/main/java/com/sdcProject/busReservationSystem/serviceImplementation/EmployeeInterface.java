package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Driver;
import org.springframework.security.core.Authentication;

public interface EmployeeInterface {

    public Driver addDriverDetails(Driver driver, Authentication authentication);

    public Driver editDriver(Driver driver,int driverId);
}
