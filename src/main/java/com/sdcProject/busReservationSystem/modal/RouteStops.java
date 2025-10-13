package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RouteStops {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int routeStopId;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private Routes route;

    private int stopOrder;

    private String stopName;

    private String address;
}
