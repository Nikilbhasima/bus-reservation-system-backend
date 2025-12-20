package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingInterface {


    Bookings bookSeats(Bookings bookings, Authentication auth,int busId);

    Bookings cancelBooking(Bookings bookings);

    List<Bookings> getUserBookings(Authentication auth);

    List<Bookings> getBookingsByBusIdAndDate(int busId, LocalDate bookingDate);

    Bookings updateBoardStatus(int bookingId);

    List<Bookings> getBookingsForAgency(Authentication auth,LocalDate bookingDate,int busId);

    Bookings cancelBooking(int bookingId,String reason);

    List<Bookings> updateJourney(Authentication auth,LocalDate date);

    List<Bookings> getActiveBookingsOfAgency(TravelAgency travelAgency);

    Float calculateTotalRevenue(TravelAgency travelAgency);

    Map<String,Integer> dataForPie(TravelAgency travelAgency);

}
