package com.sdcProject.busReservationSystem.service;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.enumFile.PaymentStatus;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import com.sdcProject.busReservationSystem.modal.Users;
import com.sdcProject.busReservationSystem.repository.BookingRepository;
import com.sdcProject.busReservationSystem.repository.BusRepository;
import com.sdcProject.busReservationSystem.repository.TravelAgencyRepository;
import com.sdcProject.busReservationSystem.repository.UserRepository;
import com.sdcProject.busReservationSystem.serviceImplementation.BookingInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingImplementation implements BookingInterface {
    private BookingRepository bookingRepository;
    private BusRepository busRepository;
    private UserRepository userRepository;
    private TravelAgencyRepository travelAgencyRepository;

    public Bookings bookSeats(Bookings bookings, Authentication auth, int busId) {

        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));

        Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));

        LocalDate today = LocalDate.now();

        bookings.setUser(users);
        bookings.setBusId(bus);
        bookings.setBookingDate(today);
        bookings.setStatus(BookingStatus.PENDING);
        bookings.setPaymentStatus(PaymentStatus.PENDING);
        bookings.setBoard(false);

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

    @Override
    public Bookings updateBoardStatus(int bookingId) {
        Bookings bookings=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
        bookings.setBoard(true);
        return bookingRepository.save(bookings);
    }

    @Override
    public List<Bookings> getBookingsForAgency(Authentication auth,LocalDate bookingDate,int busId) {
        Users users=userRepository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
        TravelAgency travelAgency=travelAgencyRepository.findByUser(users);
        List<Bus> buses=busRepository.findByTravelAgency(travelAgency);
//        Adding list of bookings
        List<Bookings> bookingsList=new ArrayList<>();

//        find bookings of each bus
        if(busId==0) {
            for (Bus bus : buses) {
                List<Bookings> bookings=bookingRepository.findByBookingDate(bus,bookingDate);
                bookingsList.addAll(bookings);
            }
        }else {
            Bus bus=busRepository.findById(busId).orElseThrow(()->new RuntimeException("Bus not found"));
                List<Bookings> bookings=bookingRepository.findByBookingDate(bus,bookingDate);
                bookingsList.addAll(bookings);
        }

        return bookingsList;
    }
}
