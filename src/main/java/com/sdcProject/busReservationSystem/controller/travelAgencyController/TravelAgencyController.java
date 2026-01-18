package com.sdcProject.busReservationSystem.controller.travelAgencyController;

import com.sdcProject.busReservationSystem.dto.AdminDashboardDTO;
import com.sdcProject.busReservationSystem.dto.SuperAdminDashboardDto;
import com.sdcProject.busReservationSystem.dto.TravelAgencyDTO;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.service.TravelAgencyImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/travelAgency")
@AllArgsConstructor
public class TravelAgencyController {

    private TravelAgencyImplementation travelAgencyImplementation;

    @PostMapping("/addTravelAgencyDetails/{ownerId}")
    public ResponseEntity<String> addTravelAgencyDetails(@RequestBody TravelAgency travelAgency, @PathVariable int ownerId) {
        travelAgencyImplementation.addTravelAgency(travelAgency, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Travel agency added successfully!");
    }

    @GetMapping("/getAgency/{id}")
    public ResponseEntity<TravelAgencyDTO> getTravelAgencyDetails( @PathVariable int id) {
                return ResponseEntity.status(HttpStatus.CREATED)
                .body(travelAgencyImplementation.getTravelAgencyById(id));
    }

    @PutMapping("/update/{agencyId}")
    public void updateTravelAgency(@RequestBody TravelAgency travelAgency, @PathVariable int agencyId) {
        travelAgencyImplementation.updateTravelAgency(travelAgency, agencyId);
    }

    @PostMapping("/editTravelAgencyDetials/{agencyId}")
    public ResponseEntity<TravelAgencyDTO> editTravelAgencyDetails(@RequestBody TravelAgency travelAgency,
                                                                   Authentication authentication,
                                                                   @PathVariable Integer agencyId) {
        TravelAgencyDTO updatedAgency = travelAgencyImplementation.editTravelAgency(travelAgency,
                authentication,
                agencyId);
        return ResponseEntity.ok(updatedAgency);
    }

    @GetMapping("/getTravelAgencyDetails")
    public ResponseEntity<TravelAgencyDTO> getTravelAgencyDetails(Authentication authentication) {
        TravelAgencyDTO travelAgencyDTO = new TravelAgencyDTO(travelAgencyImplementation.getTravelAgency(authentication));
        return ResponseEntity.status(HttpStatus.OK).body(travelAgencyDTO);
    }

    @GetMapping("/getData")
    public ResponseEntity<AdminDashboardDTO> getAdminDashboardData(Authentication authentication,
                                                                   @RequestParam("date") LocalDate date) {
        AdminDashboardDTO adminDashboardDTO = travelAgencyImplementation.getAdminDashboardData(authentication, date);
        return ResponseEntity.status(HttpStatus.OK).body(adminDashboardDTO);
    }

    @GetMapping("/getAllTravelAgencyDetails")
    public ResponseEntity<List<TravelAgencyDTO>> getAllTravelAgencyDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(travelAgencyImplementation.getTravelAgencyList());
    }

    @GetMapping("/getSuperAdminDashboardData")
    public ResponseEntity<SuperAdminDashboardDto> getSuperAdminDashboardData() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelAgencyImplementation.getSuperAdminDashboardData());
    }

}
