package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Routes;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RoutesInterface {

    public Routes addRoutes (Routes routes, Authentication auth);

    public Routes updateRoutes (Routes routes, int routeIds);

    public List<Routes> getAllRoutes(Authentication auth);
}
