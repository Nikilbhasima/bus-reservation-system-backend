package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
public class BusSchedules {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int busScheduleId;

    private Date departureDate;

    private Time departureTime;

    private Time arrivalTime;


}
