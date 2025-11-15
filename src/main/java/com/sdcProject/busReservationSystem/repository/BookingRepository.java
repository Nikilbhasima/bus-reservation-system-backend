package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Bookings,Integer> {

    List<Bookings> findByUser(Users user);

    @Query("SELECT b FROM Bookings b WHERE b.tripDate = :tripDate AND b.busId.busId = :busId")
    List<Bookings> findBookingsByBusIdAndTripDate(@Param("busId") int busId,
                                                  @Param("tripDate") LocalDate tripDate);

}
