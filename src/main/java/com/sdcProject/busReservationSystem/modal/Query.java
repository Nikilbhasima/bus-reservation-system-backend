package com.sdcProject.busReservationSystem.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Query {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private String name;
    private String email;
    private  String number;
    private String category;
    private  String message;
     private String status=  "pending";

     public Query(){

     }
public Query(String name, String email, String number, String category, String message) {
    this.name = name;
     this.email = email;
     this.number = number;
     this.category = category;
     this.message = message;

}


}
