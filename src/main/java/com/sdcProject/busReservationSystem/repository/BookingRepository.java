package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Bookings,Integer> {

//    @Query(nativeQuery = true,"select * from bookings where user_id =?1")
    List<Bookings> findByUser(Users user);

    @Query("SELECT b FROM Bookings b WHERE Date(b.tripDate) = :tripDate AND b.busId.busId = :busId")
    List<Bookings> findBookingsByBusIdAndTripDate(@Param("busId") int busId,
                                                  @Param("tripDate") LocalDate tripDate);

    @Query("SELECT b from Bookings  b WHERE b.busId=:bus AND b.bookingDate=:bookingDate")
    List<Bookings> findByBookingDate(Bus bus,LocalDate bookingDate);

    @Query("""
    SELECT b FROM Bookings b
    WHERE b.busId.travelAgency.travel_agency_id = :id
    AND b.status = :status
""")
    List<Bookings> findBookingsByTravelAgencyAndStatus(
            @Param("id") int travelAgencyId,
            @Param("status") BookingStatus status
    );


}
