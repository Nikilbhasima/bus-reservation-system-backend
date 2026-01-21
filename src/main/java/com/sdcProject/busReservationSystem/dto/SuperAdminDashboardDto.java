package com.sdcProject.busReservationSystem.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Nikil Bhasima
 * @created 12/30/2025
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuperAdminDashboardDto {

    private int activeBus;

    private int totalBooking;

    private int totalAgency;

    private int totalTrip;

    private Map<String, Integer> pieData;

    private Map<LocalDate,Integer> barCharData;

}
