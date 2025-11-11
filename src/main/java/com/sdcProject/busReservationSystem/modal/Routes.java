package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.AssignStatus;
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

    private String latitudeS;

    private String longitudeS;

    private String latitudeD;

    private String longitudeD;

    private float price;

}
