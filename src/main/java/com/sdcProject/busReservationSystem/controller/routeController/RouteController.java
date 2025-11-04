package com.sdcProject.busReservationSystem.controller.routeController;

import com.sdcProject.busReservationSystem.dto.RoutesDTO;
import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.service.RoutesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    @Autowired
    private RoutesImplementation routesImplementation;

    @PostMapping("/addRoute")
    public ResponseEntity<RoutesDTO> addRoute(@RequestBody Routes routes, Authentication auth) {
        System.out.println("this is route controller");
        RoutesDTO routesDTO= new RoutesDTO(routesImplementation.addRoutes(routes,auth)) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(routesDTO);
    }

    @PostMapping("/editRoutes/{routeId}")
    public ResponseEntity<RoutesDTO> editRoutes(@RequestBody Routes routes,@PathVariable int routeId) {

        RoutesDTO routesDTO=new RoutesDTO(routesImplementation.updateRoutes(routes,routeId)) ;
        return ResponseEntity.status(HttpStatus.OK).body(routesDTO);
    }

    @GetMapping("/getAllRoutes")
    public ResponseEntity<List<RoutesDTO>> getAllRoutes(Authentication auth ) {
        ArrayList<RoutesDTO> routesDTOs=new ArrayList<RoutesDTO>();
        List<Routes> routes=routesImplementation.getAllRoutes(auth);
        for (Routes r:routes){
            RoutesDTO routesDTO=new RoutesDTO(r);
            routesDTOs.add(routesDTO);
        }

        return  ResponseEntity.status(HttpStatus.OK).body(routesDTOs);
    }
}
