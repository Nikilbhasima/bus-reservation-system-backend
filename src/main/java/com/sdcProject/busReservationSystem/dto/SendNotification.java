package com.sdcProject.busReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikil Bhasima
 * @created 11/24/2025
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendNotification {

    String title;

    String message;
}
