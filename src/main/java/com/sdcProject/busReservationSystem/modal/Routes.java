package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int routeId;

    @ManyToOne
    @JoinColumn(name = "travelAgencyId")
    private TravelAgency travelAgency;

    private String routeName;

    private String sourceCity;

    private String destinationCity;

    private float distance;

    private int duration;
}
