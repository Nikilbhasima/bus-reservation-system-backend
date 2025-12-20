package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.AssignStatus;
import com.sdcProject.busReservationSystem.enumFile.BusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busId;

    private String busName;

    private String busRegistrationNumber;

    private int totalSeats;

    @Enumerated(EnumType.STRING)
    private BusType busType;

    @ElementCollection
    private List<String> busphotos;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "travelAgencyId")
    private TravelAgency travelAgency;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private Routes routes;

    private String currentBusLocation;

    private float increasedPrice;

    private float seatPrice;

    private float sleeperPrice;

    @ManyToOne
    @JoinColumn(name = "busScheduleId")
    private BusSchedules busSchedules;

    @ElementCollection
    private List<String> amenities;

    private AssignStatus assignStatus;

}
