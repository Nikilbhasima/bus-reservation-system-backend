package com.sdcProject.busReservationSystem.dto;


import com.sdcProject.busReservationSystem.modal.Routes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutesDTO {


    private int routeId;

    private String routeName;

    private String sourceCity;

    private String destinationCity;

    private float distance;

    private int duration;

    private String latitudeS;

    private String longitudeS;

    private String latitudeD;

    private String longitudeD;

    private float price;


    public RoutesDTO(Routes routes) {
        this.routeId = routes.getRouteId();
        this.routeName = routes.getRouteName();
        this.destinationCity = routes.getDestinationCity();
        this.sourceCity = routes.getSourceCity();
        this.longitudeS = routes.getLongitudeS();
        this.latitudeS = routes.getLatitudeS();
        this.longitudeD = routes.getLongitudeD();
        this.latitudeD = routes.getLatitudeD();
        this.distance = routes.getDistance();
        this.duration = routes.getDuration();
        this.price = routes.getPrice();
    }
}
