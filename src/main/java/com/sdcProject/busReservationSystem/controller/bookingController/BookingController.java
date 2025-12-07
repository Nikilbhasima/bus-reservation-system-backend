package com.sdcProject.busReservationSystem.controller.bookingController;

import com.sdcProject.busReservationSystem.dto.BookingDTO;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.service.BookingImplementation;
import com.sdcProject.busReservationSystem.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/busBooking")
@AllArgsConstructor
public class BookingController {

    private BookingImplementation bookingImplementation;

    @PostMapping("bookSeats/{busId}")
    public ResponseEntity<BookingDTO> bookSeats(@RequestBody Bookings bookings, @PathVariable int busId, Authentication auth) {

        BookingDTO bookingDTO=new BookingDTO( bookingImplementation.bookSeats(bookings, auth, busId));

        return ResponseEntity.status(HttpStatus.OK).body(bookingDTO);
    }

    @GetMapping("/getAllBookingsByBusIdAndDate/{busId}/{bookingDate}")
    public ResponseEntity<List<BookingDTO>> getAllBookingsByBusIdAndDate(@PathVariable int busId, @PathVariable LocalDate bookingDate) {
//        List<Bookings> bookingsList=bookingImplementation.getBookingsByBusIdAndDate(busId,bookingDate);
//        List<BookingDTO> bookingDTOList=new ArrayList<>();
//        for (Bookings bookings : bookingsList) {
//            if (!"CANCELLED".equals(bookings.getStatus())) {
//                BookingDTO bookingDTO = new BookingDTO(bookings);
//                bookingDTOList.add(bookingDTO);
//            }
//
//        }
//        return  ResponseEntity.status(HttpStatus.OK).body(bookingDTOList);
        List<BookingDTO> bookingDTOList = bookingImplementation
                .getBookingsByBusIdAndDate(busId, bookingDate)
                .stream()
                .filter(b -> !"CANCELLED".equalsIgnoreCase(String.valueOf(b.getStatus())))
                .map(BookingDTO::new)
                .toList();

        return ResponseEntity.ok(bookingDTOList);
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
        return ResponseEntity.status(HttpStatus.OK).body( bookingsDTO.stream()
                .sorted((a, b) -> b.getTripDate().compareTo(a.getTripDate())) // DESC
                .toList());
    }

//    update booking status
    @PutMapping("/updateBoardStatus/{bookingId}")
    public ResponseEntity<BookingDTO> updateBoardStatus(@PathVariable int bookingId) {
        BookingDTO bookingDTO=new BookingDTO(bookingImplementation.updateBoardStatus(bookingId));
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTO);
    }

    @GetMapping("/getBookingsForAgency")
    public ResponseEntity<List<BookingDTO>> getBookingsForAgency(Authentication auth,@RequestParam LocalDate bookingDate,@RequestParam int busId) {
        List<BookingDTO> bookingDTOList = bookingImplementation.getBookingsForAgency(auth,bookingDate,busId)
                .stream()
                .map(BookingDTO::new) // convert each Booking to BookingDTO
                .toList();

        return ResponseEntity.ok(bookingDTOList);
    }

    @PutMapping("/cancelBooking/{bookingId}")
    public ResponseEntity<ApiResponse> cancelUserBooking(@PathVariable("bookingId") int bookingId,@RequestBody Bookings bookings) {
        BookingDTO bookingDTO=new BookingDTO(bookingImplementation.cancelBooking(bookingId,bookings.getCancellationReason()));
        ApiResponse response = ApiResponse.builder()
                .message("Booking cancelled successfully")
                .httpStatus("200 OK")
                .data(bookingDTO)
                .build();

        return ResponseEntity.ok(response);

    }

}
