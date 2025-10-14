package com.sdcProject.busReservationSystem.modal;

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

    @OneToOne
    @JoinColumn(name = "routeId")
    private Routes routes;


    @OneToOne
    @JoinColumn(name = "driverId")
    private Driver driver;


}
