package com.sdcProject.busReservationSystem.dto;

import lombok.*;

/**
 * @author Nikil Bhasima
 * @created 1/16/2026
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDto {
    private int id;
    private String username;
    private String email;
    private String phoneNumber;
    private String image;
    private TravelAgencyDTO travelAgency;
    private String password;
}
