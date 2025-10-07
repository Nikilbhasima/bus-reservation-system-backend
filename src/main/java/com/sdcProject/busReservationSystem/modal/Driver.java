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
    private Routes routes;

}
