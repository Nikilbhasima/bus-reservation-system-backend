package com.sdcProject.busReservationSystem.serviceImplementation;

import com.sdcProject.busReservationSystem.modal.Bookings;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BookingInterface {


    public Bookings bookSeats(Bookings bookings, Authentication auth,int busId);

    public Bookings cancelBooking(Bookings bookings);

    public List<Bookings> getUserBookings(Authentication auth);
}
