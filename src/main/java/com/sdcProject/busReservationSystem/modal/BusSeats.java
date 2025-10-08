package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BusSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int SeatId;

    @ManyToOne
    @JoinColumn(name = "busId")
    private Bus bus;

    private String seatNumber;

    private SeatType seatType;

    private boolean seatStatus;


}
