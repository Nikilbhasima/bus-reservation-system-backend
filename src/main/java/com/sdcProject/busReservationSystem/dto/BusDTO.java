package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.enumFile.BusType;
import com.sdcProject.busReservationSystem.modal.Bus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BusDTO {

    public BusDTO(Bus bus) {
        this.busId=bus.getBusId();
        this.busType=bus.getBusType();
        this.busName=bus.getBusName();
        this.busRegistrationNumber=bus.getBusRegistrationNumber();
        this.busphotos=bus.getBusphotos();
        this.totalSeats=bus.getTotalSeats();
        this.isActive=bus.isActive();
    }

    private int busId;

    private String busName;

    private String busRegistrationNumber;

    private int totalSeats;

    private BusType busType;

    private List<String> busphotos;

    private boolean isActive;

}
