package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.enumFile.BusType;
import com.sdcProject.busReservationSystem.modal.Bus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BusDTO {



    private int busId;

    private String busName;

    private String busRegistrationNumber;

    private int totalSeats;

    private BusType busType;

    private List<String> busphotos;

    private boolean isActive;

    private String currentBusLocation;

    private float increasedPrice;

    private List<String> amenities;


    public BusDTO(Bus bus) {
        this.busId=bus.getBusId();
        this.busType=bus.getBusType();
        this.busName=bus.getBusName();
        this.busRegistrationNumber=bus.getBusRegistrationNumber();
        this.busphotos=bus.getBusphotos();
        this.totalSeats=bus.getTotalSeats();
        this.isActive=bus.isActive();
        this.currentBusLocation=bus.getCurrentBusLocation();
        this.increasedPrice=bus.getIncreasedPrice();
        this.amenities=bus.getAmenities();
    }

}
