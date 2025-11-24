package com.sdcProject.busReservationSystem.controller.busController;

import com.sdcProject.busReservationSystem.dto.BusDTO;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.service.BusImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("i want to edit the bus");
        BusDTO busDTO=new BusDTO(busImplementation.editBus(bus, busId));
        return  ResponseEntity.status(HttpStatus.OK).body(busDTO);
    }

    @GetMapping("/getAllBusByTravelAgency")
    public ResponseEntity<List<BusDTO>> getAllBusByTravelAgency( Authentication auth) {
        List<Bus> buses=busImplementation.findAllBuses(auth);
        List<BusDTO> busDTOs=new ArrayList<>();
        for (Bus bus : buses) {
            BusDTO busDTO=new BusDTO(bus);
            busDTOs.add(busDTO);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(busDTOs);
    }

    @GetMapping("/getBusById/{id}/{date}")
    public ResponseEntity<BusDTO> getBusById(@PathVariable("id") int busId,@PathVariable("date") LocalDate date) {
        BusDTO busDTO=new BusDTO(busImplementation.getBusById(busId,date));
        return ResponseEntity.status(HttpStatus.OK).body(busDTO);
    }

    @GetMapping("/getBusById/{id}")
    public ResponseEntity<BusDTO> getBusById(@PathVariable("id") int busId) {
        BusDTO busDTO=new BusDTO(busImplementation.getBusById(busId));
        return ResponseEntity.status(HttpStatus.OK).body(busDTO);
    }

    @GetMapping("/getBusesByRoute/{travelDate}")
    public ResponseEntity<List<BusDTO>> getBusesByRoute(    @RequestParam String sourceCity,
                                                            @RequestParam String destinationCity,
                                                            @PathVariable LocalDate travelDate) {
        Routes routes=new Routes();
        routes.setSourceCity(sourceCity);
        routes.setDestinationCity(destinationCity);
        List<Bus> buses=busImplementation.getBusesByRoute(routes,travelDate);
        List<BusDTO> busDTOs=new ArrayList<>();
        for (Bus bus : buses) {
            BusDTO busDTO=new BusDTO(bus);
            busDTOs.add(busDTO);
        }
        return
                ResponseEntity.status(HttpStatus.OK).body(busDTOs);
    }

    @PutMapping("/switchCurrentLocation")
    public ResponseEntity<BusDTO> switchCurrentLocation(@RequestParam int busId) {
        System.out.println("i want to switch the current location");
        BusDTO busDTO=new BusDTO(busImplementation.changeBusLocation(busId));
        return  ResponseEntity.status(HttpStatus.OK).body(busDTO);
    }


    @PutMapping("/updateBusStatus")
    public ResponseEntity<BusDTO> updateBusStatus(@RequestParam int busId) {
        System.out.println("i want to update status");
        BusDTO busDTO=new BusDTO(busImplementation.updateBusStatus(busId));
        return  ResponseEntity.status(HttpStatus.OK).body(busDTO);
    }


}
