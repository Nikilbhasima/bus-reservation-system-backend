package com.sdcProject.busReservationSystem.controller.busScheduleController;

import com.sdcProject.busReservationSystem.dto.BusScheduleDTO;
import com.sdcProject.busReservationSystem.modal.BusSchedules;
import com.sdcProject.busReservationSystem.service.BusScheduleImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/busSchedule")
@AllArgsConstructor
public class BusScheduleController {

    private BusScheduleImplementation busScheduleImplementation;

    @PostMapping("/addBusSchedule")
    public ResponseEntity<BusScheduleDTO>  addBusSchedule(@RequestBody BusSchedules busSchedules, Authentication authentication) {
        BusScheduleDTO busScheduleDTO=new BusScheduleDTO(busScheduleImplementation.addBusSchedules(busSchedules,authentication));
        return ResponseEntity.status(HttpStatus.CREATED).body(busScheduleDTO);
    }

    @PostMapping("/editBusSchedule/{busScheduleId}")
    public ResponseEntity<BusScheduleDTO> editBusSchedule(@RequestBody BusSchedules busSchedules, @PathVariable int busScheduleId) {
        BusScheduleDTO busScheduleDTO=new BusScheduleDTO(busScheduleImplementation.editBusSchedules(busSchedules,busScheduleId));
        return ResponseEntity.status(HttpStatus.OK).body(busScheduleDTO);
    }
    @GetMapping("/getBusSchedulesByTravelAgency")
    public ResponseEntity<List<BusScheduleDTO>> getBusSchedulesByTravelAgency(Authentication authentication) {

        List<BusSchedules>  busSchedules=busScheduleImplementation.getAllBusSchedules(authentication);
        ArrayList<BusScheduleDTO>  busScheduleDTOS=new ArrayList<>();
        for(BusSchedules busSchedule:busSchedules){
            BusScheduleDTO busScheduleDTO=new BusScheduleDTO(busSchedule);
            busScheduleDTOS.add(busScheduleDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(busScheduleDTOS);
    }

}
