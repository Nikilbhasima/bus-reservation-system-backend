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

    private String period;

    private LocalTime departureTime;



    public BusScheduleDTO(BusSchedules busSchedules) {
        this.busScheduleId=busSchedules.getBusScheduleId();
        this.period=busSchedules.getPeriod();
        this.departureTime=busSchedules.getDepartureTime();}
}
