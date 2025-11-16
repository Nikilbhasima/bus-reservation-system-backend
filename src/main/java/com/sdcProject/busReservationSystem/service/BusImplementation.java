package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.enumFile.AssignStatus;
import com.sdcProject.busReservationSystem.enumFile.BusType;
import com.sdcProject.busReservationSystem.modal.*;
import com.sdcProject.busReservationSystem.repository.*;
import com.sdcProject.busReservationSystem.serviceImplementation.BusInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusImplementation implements BusInterface {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TravelAgencyRepository travelAgencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private BusScheduleRespository busScheduleRepository;

    @Override
    public Bus addBus(Bus bus, Authentication authentication) {
        // Validate user
        Users user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelAgency agency = travelAgencyRepository.findByUser(user);
        if (agency == null) {
            throw new RuntimeException("Travel agency not found for user");
        }
        bus.setTravelAgency(agency);
        bus.setAssignStatus(AssignStatus.UNASSIGN);

        // Validate and set routes - ADD NULL CHECK
        if (bus.getRoutes() == null || bus.getRoutes().getRouteId() == 0) {
            throw new RuntimeException("Route information is required");
        }
        Routes routes = routesRepository.findById(bus.getRoutes().getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        bus.setRoutes(routes);

        bus.setCurrentBusLocation(routes.getSourceCity());

        // Validate and set bus schedules - ADD NULL CHECK
        if (bus.getBusSchedules() == null || bus.getBusSchedules().getBusScheduleId() == 0) {
            throw new RuntimeException("Bus schedule information is required");
        }
        BusSchedules busSchedules = busScheduleRepository
                .findById(bus.getBusSchedules().getBusScheduleId())
                .orElseThrow(() -> new RuntimeException("BusSchedule not found"));
        bus.setBusSchedules(busSchedules);

        return busRepository.save(bus);
    }

    @Override
    public Bus editBus(Bus bus, int bus_id) {
        Bus bus1 = busRepository.findById(bus_id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // Update basic fields
        bus1.setBusType(bus.getBusType());
        bus1.setBusName(bus.getBusName());
        bus1.setBusRegistrationNumber(bus.getBusRegistrationNumber());
        bus1.setActive(bus.isActive());
        bus1.setTotalSeats(bus.getTotalSeats());
        bus1.setBusphotos(bus.getBusphotos());
        bus1.setIncreasedPrice(bus.getIncreasedPrice());
        bus1.setAmenities(bus.getAmenities());
        bus1.setAssignStatus(bus.getAssignStatus());

        // ✅ Update routes (fixed - was setting on 'bus' instead of 'bus1')
        if (bus.getRoutes() != null && bus.getRoutes().getRouteId() != 0) {
            Routes routes = routesRepository.findById(bus.getRoutes().getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            bus1.setRoutes(routes);

            bus1.setCurrentBusLocation(routes.getSourceCity());
        }

        // ✅ Update bus schedules (fixed - was setting on 'bus' instead of 'bus1')
        if (bus.getBusSchedules() != null && bus.getBusSchedules().getBusScheduleId() != 0) {
            BusSchedules busSchedules = busScheduleRepository
                    .findById(bus.getBusSchedules().getBusScheduleId())
                    .orElseThrow(() -> new RuntimeException("BusSchedule not found"));
            bus1.setBusSchedules(busSchedules);  // ✅ Fixed
        }

        return busRepository.save(bus1);
    }

    @Override
    public List<Bus> findAllBuses(Authentication auth) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Bus> buses=busRepository.findByTravelAgency(travelAgency);
        return buses;
    }

    @Override
    public Bus getBusById(int busId,LocalDate date) {
        Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));

        LocalDate today = LocalDate.now();
        int daysDifference = (int)ChronoUnit.DAYS.between(today, date);

        if(daysDifference>0) {
            for (int i=daysDifference;i>0;i--){
                if(bus.getCurrentBusLocation().equals(bus.getRoutes().getSourceCity())){
                    bus.setCurrentBusLocation(bus.getRoutes().getDestinationCity());


                }else if(bus.getCurrentBusLocation().equals(bus.getRoutes().getDestinationCity())){
                    bus.setCurrentBusLocation(bus.getRoutes().getSourceCity());

                }
            }
        }
        return bus;
    }

    @Override
    public List<Bus> getBusesByRoute(Routes routes, LocalDate travelDate) {
//get list of route
        List<Routes> listOfRoutes=routesRepository.findMatchingRoutes(routes.getSourceCity(),routes.getDestinationCity());
        if (listOfRoutes == null) {
            throw new RuntimeException("Routes not found");
        }
//get list of buses
        ArrayList<Bus> buses=new ArrayList<Bus>();
        for (Routes route : listOfRoutes) {
            List<Bus> buses1=busRepository.findByRoutes(route);
            buses.addAll(buses1);
        }

        LocalDate today = LocalDate.now();
        int daysDifference = (int)ChronoUnit.DAYS.between(today, travelDate);
        System.out.println("date difference"+daysDifference);

        ArrayList<Bus> finalBusList=new ArrayList<>();
//        finding current location of bus by date
        if (daysDifference >0) {
            for (int i=daysDifference;i>0;i--) {
                for (Bus bus : buses) {
                    if(bus.getCurrentBusLocation().equals(bus.getRoutes().getSourceCity())){
                        bus.setCurrentBusLocation(bus.getRoutes().getDestinationCity());


                    }else if(bus.getCurrentBusLocation().equals(bus.getRoutes().getDestinationCity())){
                        bus.setCurrentBusLocation(bus.getRoutes().getSourceCity());

                    }
                }
            }

            for (Bus bus : buses) {
                if (bus.getCurrentBusLocation().toLowerCase().contains(routes.getSourceCity().toLowerCase())) {
                    finalBusList.add(bus);
                }
            }
            return finalBusList;
        }else {
            for (Bus bus : buses) {
                if (bus.getCurrentBusLocation().toLowerCase().contains(routes.getSourceCity().toLowerCase())) {
                    finalBusList.add(bus);
                }
            }
            return buses;
        }

    }
}
