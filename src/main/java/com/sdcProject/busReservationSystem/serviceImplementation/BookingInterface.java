package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Bookings;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface BookingInterface {


    public Bookings bookSeats(Bookings bookings, Authentication auth,int busId);

    public Bookings cancelBooking(Bookings bookings);

    public List<Bookings> getUserBookings(Authentication auth);

    public List<Bookings> getBookingsByBusIdAndDate(int busId, LocalDate bookingDate);

    public Bookings updateBoardStatus(int bookingId);

    public List<Bookings> getBookingsForAgency(Authentication auth,LocalDate bookingDate,int busId);

    public Bookings cancelBooking(int bookingId);
}
