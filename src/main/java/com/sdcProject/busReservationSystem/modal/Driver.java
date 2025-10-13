package com.sdcProject.busReservationSystem.modal;

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

    @Lob
    private String driver_photo;

    private String driver_license_number;


    @Lob
    private String license_photo;
    private String Driver_name;

    private String Driver_phone;

    private String Driver_email;

    private String Driver_address;

    private String Driver_photo;

    private String driver_license_number;

    @ElementCollection
    private ArrayList<String> driver_photo;

    @ManyToOne
    @JoinColumn(name = "travelAgencyId")
    private TravelAgency travelAgency;

    @OneToOne
    @JoinColumn(name = "BusId")
    private Bus bus;
    private Routes routes;

}
