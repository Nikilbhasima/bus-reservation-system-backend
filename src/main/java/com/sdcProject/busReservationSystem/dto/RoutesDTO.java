package com.sdcProject.busReservationSystem.dto;


import com.sdcProject.busReservationSystem.modal.Routes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutesDTO {

    public RoutesDTO(Routes routes) {
        this.routeId = routes.getRouteId();
        this.routeName = routes.getRouteName();
        this.destinationCity = routes.getDestinationCity();
        this.sourceCity = routes.getSourceCity();
        this.latitude = routes.getLatitude();
        this.longitude = routes.getLongitude();
        this.distance = routes.getDistance();
        this.duration = routes.getDuration();
        this.price = routes.getPrice();
    }
    private int routeId;

    private String routeName;

    private String sourceCity;

    private String destinationCity;

    private float distance;

    private int duration;

    private String latitude;

    private String longitude;

    private float price;
}
