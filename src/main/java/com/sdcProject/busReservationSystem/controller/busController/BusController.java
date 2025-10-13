package com.sdcProject.busReservationSystem.controller.busController;

import com.sdcProject.busReservationSystem.dto.BusDTO;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.service.BusImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bus")
public class BusController {

    @Autowired
    private BusImplementation busImplementation;

    @PostMapping("/addBus")
    public ResponseEntity<BusDTO> addBus(@RequestBody Bus bus, Authentication auth) {
        BusDTO busDTO=new BusDTO(busImplementation.addBus(bus, auth));
        return  ResponseEntity.status(HttpStatus.CREATED).body(busDTO);
    }

    @PostMapping("/editBus/{busId}")
    public ResponseEntity<BusDTO> editBus(@RequestBody Bus bus, @PathVariable int busId ) {
        BusDTO busDTO=new BusDTO(busImplementation.editBus(bus, busId));
        return  ResponseEntity.status(HttpStatus.OK).body(busDTO);
    }
}
