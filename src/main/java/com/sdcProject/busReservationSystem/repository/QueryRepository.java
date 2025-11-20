package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository  extends JpaRepository<Query, Integer> {
    List<Query> findByEmail(String email);

}

