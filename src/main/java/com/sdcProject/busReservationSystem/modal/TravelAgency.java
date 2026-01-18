package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travel_agency_id;

    private String registration_number;

    private String travel_agency_name;

    private String agency_logo;

    private String address;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "userId")
    private Users user;

}
