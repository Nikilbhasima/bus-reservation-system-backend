package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int userId;

    private String username;

    private String email;

    private String phoneNumber;

    private String address;

    private String gender;

    private String password;

    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {
            @JoinColumn(name = "role_id")
    })
    List<Roles> roles;

}
