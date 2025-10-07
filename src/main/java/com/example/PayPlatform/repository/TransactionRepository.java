package com.example.PayPlatform.repository;

import com.example.PayPlatform.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByFromUser_IdOrToUser_Id(Long fromId, Long toId);
    List<Transaction> findByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
