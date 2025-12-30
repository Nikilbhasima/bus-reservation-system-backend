package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelAgencyRepository extends JpaRepository<TravelAgency, Integer> {


    TravelAgency findByUser(Users user);

    @Query(value = "SELECT COUNT(*) as total FROM travel_agency",nativeQuery = true)
    Integer countAgency();
}
