package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.BookingRepository;
import com.sdcProject.busReservationSystem.repository.BusRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.BookingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingImplementation implements BookingInterface {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Bookings bookSeats(Bookings bookings, Authentication auth, int busId) {

        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));

        Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));

        LocalDate today = LocalDate.now();

        bookings.setUser(users);
        bookings.setBusId(bus);
        bookings.setBookingDate(today);
        bookings.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(bookings);
    }

    @Override
    public Bookings cancelBooking(Bookings bookings) {
        Bookings bookings1=bookingRepository.findById(bookings.getBookingId()).orElseThrow(()->new RuntimeException("Booking not found"));
        bookings1.setStatus(BookingStatus.CANCELLED);
        bookings1.setCancellationReason(bookings.getCancellationReason());

        return bookingRepository.save(bookings1);
    }

    @Override
    public List<Bookings> getUserBookings(Authentication auth) {
        Users user=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<Bookings> getBookingsByBusIdAndDate(int busId, LocalDate bookingDate) {
        List<Bookings> bookingsList=bookingRepository.findBookingsByBusIdAndTripDate(busId,bookingDate);
        return bookingsList;
    }
}
