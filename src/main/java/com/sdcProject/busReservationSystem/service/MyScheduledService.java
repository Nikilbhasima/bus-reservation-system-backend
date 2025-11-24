package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikil Bhasima
 * @created 11/24/2025
 **/
@Service
@RequiredArgsConstructor
public class MyScheduledService {

    private final BusRepository busRepository;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kathmandu") // runs daily at 12:00 AM
    @Transient
    public void updateBusData(){


        List<Bus> busList=busRepository.findAll();
        List<Bus> finalBusList=new ArrayList<>();
        for(Bus bus:busList){
            if (!bus.getCurrentBusLocation().equalsIgnoreCase(bus.getRoutes().getDestinationCity())) {
                bus.setCurrentBusLocation(bus.getRoutes().getDestinationCity());
                finalBusList.add(bus);
            } else {
                bus.setCurrentBusLocation(bus.getRoutes().getSourceCity());
                finalBusList.add(bus);
            }


        }
        busRepository.saveAll(finalBusList);

    }



}
