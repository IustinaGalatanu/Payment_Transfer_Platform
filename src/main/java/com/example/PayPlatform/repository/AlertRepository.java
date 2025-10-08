package com.example.PayPlatform.repository;

import com.example.PayPlatform.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {

    List<Alert> findByUser_Id(Long userId);


}
