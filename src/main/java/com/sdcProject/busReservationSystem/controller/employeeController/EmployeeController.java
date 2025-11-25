package com.sdcProject.busReservationSystem.controller.employeeController;

import com.sdcProject.busReservationSystem.dto.BookingDTO;
import com.sdcProject.busReservationSystem.dto.EmployeeDTO;
import com.sdcProject.busReservationSystem.dto.SendNotification;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Driver;
import com.sdcProject.busReservationSystem.serviceImplementation.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeInterface employeeInterface;

    @PostMapping("addEmployee")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody Driver driver, Authentication authentication) {
        EmployeeDTO employeeDTO=new EmployeeDTO(employeeInterface.addDriverDetails(driver, authentication));
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @PostMapping("editEmployee/{employeeId}")
    public ResponseEntity<EmployeeDTO> editEmployee(@RequestBody Driver driver, @PathVariable int employeeId) {
        System.out.println("Employee edit::::");
        EmployeeDTO employeeDTO=new EmployeeDTO(employeeInterface.editDriver(driver, employeeId));
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @GetMapping("/getEmployeeByTravelAgency")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByTravelAgency(Authentication authentication) {
        List<Driver>  drivers=employeeInterface.getDriversByAgency(authentication);
        List<EmployeeDTO> employeeDTOS=new ArrayList<>();
        for(Driver driver:drivers){
            employeeDTOS.add(new EmployeeDTO(driver));

        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTOS);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable  int id) {

        EmployeeDTO employeeDTO=new EmployeeDTO(employeeInterface.getDriverById(id));

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @GetMapping("/getListofBookingsByDateAndDriver/{date}")
    public ResponseEntity<List<BookingDTO>> getListonBookingsByDateAndDriver(Authentication authentication, @PathVariable("date") LocalDate date) {
        List<BookingDTO> bookingDTOS=new ArrayList<>();
        List<Bookings> bookingsList=employeeInterface.bookingsByDriverAndDate(authentication,date);

        for(Bookings booking:bookingsList){
            bookingDTOS.add(new BookingDTO(booking));

        }
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTOS);
    }

    @PostMapping("/sendPassengerNotification/{busId}/{bookingDate}")
    public ResponseEntity<?> sendPassengerNotification(@PathVariable int busId, @PathVariable LocalDate bookingDate, @RequestBody SendNotification sendNotification) {
        return ResponseEntity.status(HttpStatus.OK).body( employeeInterface.sendNotificationToPassenger(busId,bookingDate,sendNotification));
    }

    @GetMapping("/getEmployeeData")
    public ResponseEntity<EmployeeDTO> getEmployeeData(Authentication authentication) {

        EmployeeDTO employeeDTO=new EmployeeDTO(employeeInterface.getDriverData(authentication));

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @PutMapping("/unAssignEmployeeBus/{employeeId}")
    public ResponseEntity<EmployeeDTO> unAssignEmployeeBus(@PathVariable int employeeId) {
        EmployeeDTO employeeDTO=new EmployeeDTO(employeeInterface.unassignDriver(employeeId));
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @PutMapping("/assignDriverBus")
    public ResponseEntity<EmployeeDTO> assignDriverBus(@RequestParam int busId,@RequestParam int driverId) {
        EmployeeDTO  employeeDTO=new EmployeeDTO(employeeInterface.assignDriver(driverId,busId));
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }
}
