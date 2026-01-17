package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.dto.AdminDashboardDTO;
import com.sdcProject.busReservationSystem.dto.OwnerDto;
import com.sdcProject.busReservationSystem.dto.SuperAdminDashboardDto;
import com.sdcProject.busReservationSystem.dto.TravelAgencyDTO;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

public interface TravelAgencyInterface {

    void addTravelAgency(@RequestBody TravelAgency travelAgency, int ownerId);

    TravelAgencyDTO getTravelAgencyById(int id);

    void updateTravelAgency(@RequestBody TravelAgency travelAgency, int agencyId);

    TravelAgencyDTO editTravelAgency(@RequestBody TravelAgency travelAgency,
                                     Authentication authentication,
                                     Integer ownerId);

    TravelAgency getTravelAgency(Authentication authentication);

    AdminDashboardDTO getAdminDashboardData(Authentication authentication, LocalDate date);

    List<TravelAgencyDTO> getTravelAgencyList();

    SuperAdminDashboardDto getSuperAdminDashboardData();

}
