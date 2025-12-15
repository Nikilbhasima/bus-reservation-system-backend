package com.sdcProject.busReservationSystem.modal;

import com.sdcProject.busReservationSystem.enumFile.AssignStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    private String driver_name;

    private String driver_phone;

    private String driver_email;

    private String driver_address;

    private String driver_photo;

    private String driver_license_number;

    private String license_photo;

    @ManyToOne
    @JoinColumn(name = "travelAgencyId")
    private TravelAgency travelAgency;

    @OneToOne
    @JoinColumn(name = "BusId")
    private Bus bus;



}
