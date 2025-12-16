package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.enumFile.AssignStatus;
import com.sdcProject.busReservationSystem.enumFile.BusType;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.BusSchedules;
import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
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

    private Routes routes;

    private BusSchedules busSchedules;

    private AssignStatus assignStatus;

    private TravelAgency travelAgency;

    private float seatPrice;

    private float sleeperPrice;



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
        this.routes=bus.getRoutes();
        this.busSchedules=bus.getBusSchedules();
        this.assignStatus=bus.getAssignStatus();
        this.travelAgency=bus.getTravelAgency();
        this.seatPrice=bus.getSeatPrice();
        this.sleeperPrice=bus.getSleeperPrice();
    }

}
