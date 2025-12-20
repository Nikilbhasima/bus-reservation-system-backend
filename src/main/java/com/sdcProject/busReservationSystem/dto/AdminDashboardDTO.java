package com.sdcProject.busReservationSystem.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Nikil Bhasima
 * @created 12/20/2025
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardDTO {

    private int totalBuses;

    private int totalDrivers;

    private int activeBookings;

    private double totalRevenue;

    private Map<String, Integer> pieData;

    private Map<LocalDate,Integer> barCharData;
}
