package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.modal.BusSchedules;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class BusScheduleDTO {


    private int busScheduleId;

    private LocalDate departureDate;

    private LocalTime departureTime;



    public BusScheduleDTO(BusSchedules busSchedules) {
        this.busScheduleId=busSchedules.getBusScheduleId();
        this.departureDate=busSchedules.getDepartureDate();
        this.departureTime=busSchedules.getDepartureTime();}
}
