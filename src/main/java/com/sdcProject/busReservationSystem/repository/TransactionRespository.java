package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRespository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionUuid(String transactionUuid);
}
