package com.sdcProject.busReservationSystem.dto;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.enumFile.PaymentStatus;
import com.sdcProject.busReservationSystem.modal.Bookings;
import com.sdcProject.busReservationSystem.modal.Bus;
import com.sdcProject.busReservationSystem.modal.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingDTO {


    private int bookingId;

    private Users user;

    private int totalSeats;

    private LocalDate bookingDate;

    private LocalDate tripDate;

    private BookingStatus status;

    private PaymentStatus paymentStatus;

    private String cancellationReason;


    private List<String> seatName;

    private Bus busId;

    public BookingDTO(Bookings bookings) {
        this.bookingId = bookings.getBookingId();
        this.user = bookings.getUser();
        this.totalSeats = bookings.getTotalSeats();
        this.bookingDate = bookings.getBookingDate();
        this.tripDate = bookings.getTripDate();
        this.status = bookings.getStatus();
        this.paymentStatus = bookings.getPaymentStatus();
        this.cancellationReason = bookings.getCancellationReason();
        this.seatName = bookings.getSeatName();
        this.busId = bookings.getBusId();

    }

}
