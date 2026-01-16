package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.modal.TravelAgency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelAgencyDTO {

    public TravelAgencyDTO(TravelAgency travelAgency) {
        this.travel_agency_id = travelAgency.getTravel_agency_id();
        this.registration_number = travelAgency.getRegistration_number();
        this.agency_logo = travelAgency.getAgency_logo();
        this.travel_agency_name = travelAgency.getAddress();
        this.address = travelAgency.getAddress();
    }

    private int travel_agency_id;

    private String registration_number;

    private String travel_agency_name;

    private String agency_logo;

    private String address;
}
