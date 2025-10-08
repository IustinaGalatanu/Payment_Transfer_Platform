package com.example.PayPlatform.service;

import com.example.PayPlatform.model.Alert;
import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.dto.AlertDto;
import com.example.PayPlatform.model.enums.AlertType;

import java.math.BigDecimal;
import java.util.List;


public interface AlertService {

    public void checkForAlerts(Transaction transaction, BigDecimal balance);

    public void save (Long userId, AlertType type, String message);

    public List<AlertDto> findAll();

    public List<AlertDto> findByUserId(Long userId);


}
