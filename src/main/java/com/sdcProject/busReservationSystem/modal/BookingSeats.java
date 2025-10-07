package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.PassengerGender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class BookingSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingSeatsId;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Bookings bookings;

    private String passengerName;

    @Enumerated(EnumType.STRING)
    private PassengerGender gender;

    private Boolean isBoared;


}
