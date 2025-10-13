package com.sdcProject.busReservationSystem.controller.employeeController;

import com.sdcProject.busReservationSystem.dto.EmployeeDTO;
import com.sdcProject.busReservationSystem.modal.Driver;
import com.sdcProject.busReservationSystem.serviceImplementation.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        EmployeeDTO employeeDTO=new EmployeeDTO(employeeInterface.editDriver(driver, employeeId));
        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }
}
