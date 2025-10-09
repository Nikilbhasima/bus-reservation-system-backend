package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.enumFile.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "busScheduleId")
    private BusSchedules busSchedules;

    private int totatSeats;

    private Date bookingDate;

    private BookingStatus status;

    private PaymentStatus paymentStatus;

    private String cancellationReason;
}
