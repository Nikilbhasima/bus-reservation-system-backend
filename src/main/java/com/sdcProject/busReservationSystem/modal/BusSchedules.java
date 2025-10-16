package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Getter
@Setter
public class BusSchedules {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int busScheduleId;

    private LocalDate departureDate;

    private LocalTime departureTime;



    @ManyToOne
    @JoinColumn(name = "travelAgencyId")
    private TravelAgency travelAgency;


}
