package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.BookingStatus;
import com.sdcProject.busReservationSystem.enumFile.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    private int totalSeats;

    private LocalDate bookingDate;

    private LocalDate tripDate;

    private BookingStatus status;

    private PaymentStatus paymentStatus;

    private String cancellationReason;

    @ElementCollection
    private List<String> seatName;

    @ManyToOne
    @JoinColumn(name = "busId")
    private Bus busId;


}
