package com.sdcProject.busReservationSystem.controller.travelAgencyController;

import com.sdcProject.busReservationSystem.dto.TravelAgencyDTO;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.service.TravelAgencyImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/travelAgency")
public class TravelAgencyController {

    @Autowired
    private TravelAgencyImplementation travelAgencyImplementation;

    @PostMapping("/addTravelAgencyDetails")
    public ResponseEntity<String> addTravelAgencyDetails(@RequestBody TravelAgency travelAgency, Authentication authentication) {
        travelAgencyImplementation.addTravelAgency(travelAgency,authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Travel agency added successfully!");
    }

    @PostMapping("/editTravelAgencyDetials")
    public ResponseEntity<TravelAgencyDTO> editTravelAgencyDetails(@RequestBody TravelAgency travelAgency, Authentication authentication) {
        TravelAgencyDTO updatedAgency = travelAgencyImplementation.editTravelAgency(travelAgency, authentication);
        return ResponseEntity.ok(updatedAgency);    }

}
