package com.sdcProject.busReservationSystem.controller.bookingController;

import com.sdcProject.busReservationSystem.dto.BookingDTO;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.service.BookingImplementation;
import com.sdcProject.busReservationSystem.serviceImplementation.BookingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/busBooking")
public class BookingController {

    @Autowired
    private BookingImplementation bookingImplementation;

    @PostMapping("bookSeats/{busId}")
    public ResponseEntity<BookingDTO> bookSeats(@RequestBody Bookings bookings, @PathVariable int busId, Authentication auth) {

        BookingDTO bookingDTO=new BookingDTO( bookingImplementation.bookSeats(bookings, auth, busId));

        return ResponseEntity.status(HttpStatus.OK).body(bookingDTO);
    }

    @GetMapping("/getAllBookingsByBusIdAndDate/{busId}/{bookingDate}")
    public ResponseEntity<List<BookingDTO>> getAllBookingsByBusIdAndDate(@PathVariable int busId, @PathVariable LocalDate bookingDate) {
        List<Bookings> bookingsList=bookingImplementation.getBookingsByBusIdAndDate(busId,bookingDate);
        List<BookingDTO> bookingDTOList=new ArrayList<>();
        for (Bookings bookings : bookingsList) {
            BookingDTO bookingDTO=new BookingDTO(bookings);
            bookingDTOList.add(bookingDTO);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(bookingDTOList);
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<BookingDTO> cancelBooking(@RequestBody Bookings bookings) {
        BookingDTO bookingDTO=new BookingDTO( bookingImplementation.cancelBooking(bookings));
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTO);
    }

    @GetMapping("/getUserBookings")
    public ResponseEntity<List<BookingDTO>> getUserBookings(Authentication auth) {
        List<Bookings> bookings=bookingImplementation.getUserBookings(auth);
        List<BookingDTO> bookingsDTO=new ArrayList<>();
        for(Bookings booking:bookings){
            bookingsDTO.add(new BookingDTO(booking));
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookingsDTO);
    }
}
