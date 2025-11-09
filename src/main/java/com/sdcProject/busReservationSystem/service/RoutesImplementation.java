package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.RoutesRepository;
import com.sdcProject.busReservationSystem.repository.TravelAgencyRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.RoutesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoutesImplementation implements RoutesInterface {

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelAgencyRepository travelAgencyRepository;

    @Override
    public Routes addRoutes(Routes routes, Authentication auth) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        if (travelAgency == null) {
            throw new RuntimeException("Travel agency not found for user: " + auth.getName());
        }

        routes.setTravelAgency(travelAgency);
        return routesRepository.save(routes);
    }

    @Override
    public Routes updateRoutes(Routes routes, int routeIds) {
        Routes  routes1=routesRepository.findById(routeIds).orElseThrow(()->new RuntimeException("Route not found"));
        routes1.setRouteName(routes.getRouteName());
        routes1.setDuration(routes.getDuration());
        routes1.setDistance(routes.getDistance());
        routes1.setLatitudeS(routes.getLatitudeS());
        routes1.setLongitudeS(routes.getLongitudeS());
        routes1.setLongitudeD(routes.getLongitudeD());
        routes1.setLatitudeD(routes.getLatitudeD());
        routes1.setDestinationCity(routes.getDestinationCity());
        routes1.setSourceCity(routes.getSourceCity());
        routes1.setPrice(routes.getPrice());

        return routesRepository.save(routes1);
    }

    @Override
    public List<Routes> getAllRoutes(Authentication auth) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Routes> routes=routesRepository.findByTravelAgency(travelAgency);
        return routes;
    }

    @Override
    public Routes getRouteById(int routeId) {
        Routes routes=routesRepository.findById(routeId).orElseThrow(()->new RuntimeException("Route not found"));
        return routes;
    }
}
