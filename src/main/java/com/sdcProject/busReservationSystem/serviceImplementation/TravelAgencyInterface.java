package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.dto.TravelAgencyDTO;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

public interface TravelAgencyInterface {

    public void addTravelAgency(@RequestBody TravelAgency travelAgency, Authentication authentication);

    public TravelAgencyDTO editTravelAgency(@RequestBody TravelAgency travelAgency, Authentication authentication);

    public TravelAgency getTravelAgency(Authentication authentication);
}
