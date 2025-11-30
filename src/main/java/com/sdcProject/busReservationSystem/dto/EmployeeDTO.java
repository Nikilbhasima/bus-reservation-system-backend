package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Driver;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {


    private int driverId;

    private String driver_name;

    private String driver_phone;

    private String driver_email;

    private String driver_address;

    private String driver_photo;

    private String driver_license_number;

    private Bus Bus;

    private String license_photo;
    public EmployeeDTO(Driver driver) {
        this.driverId=driver.getDriverId();
        this.driver_address=driver.getDriver_address();
        this.driver_name=driver.getDriver_name();
        this.driver_phone=driver.getDriver_phone();
        this.driver_email=driver.getDriver_email();
        this.driver_license_number=driver.getDriver_license_number();
        this.license_photo=driver.getLicense_photo();
        this.driver_photo=driver.getDriver_photo();
        this.Bus=driver.getBus();
    }

}
