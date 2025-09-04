package com.sdcProject.busReservationSystem.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
