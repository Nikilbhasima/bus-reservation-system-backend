package com.sdcProject.busReservationSystem.utils;

import lombok.*;

/**
 * @author Nikil Bhasima
 * @created 11/30/2025
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private String message;
    private String httpStatus;
    private Object data;
}
