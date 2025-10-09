package com.example.PayPlatform.service.impl;

import com.example.PayPlatform.model.Alert;
import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.User;
import com.example.PayPlatform.model.dto.AlertDto;
import com.example.PayPlatform.model.enums.AlertType;
import com.example.PayPlatform.model.enums.TransactionStatus;
import com.example.PayPlatform.repository.AlertRepository;
import com.example.PayPlatform.repository.TransactionRepository;
import com.example.PayPlatform.repository.UserRepository;
import com.example.PayPlatform.service.AlertService;
import com.example.PayPlatform.service.mapper.AlertMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)

public class AlertServiceImpl implements AlertService {

    private final AlertMapper alertMapper;
    private final TransactionRepository transactionRepository;
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public AlertServiceImpl(AlertMapper alertMapper, TransactionRepository transactionRepository, AlertRepository alertRepository, UserRepository userRepository) {
        this.alertMapper = alertMapper;
        this.transactionRepository = transactionRepository;
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void checkForAlerts(Transaction transaction, BigDecimal balance) {
        BigDecimal limit =new BigDecimal("10000");
        Long userId=null;
        if(transaction.getFromUser()!=null) {
            userId=transaction.getFromUser().getId();
        } else if(transaction.getToUser()!=null) {
            userId=transaction.getToUser().getId();
        }
        if(transaction.getAmount().compareTo(limit)>0) {
            save(userId,AlertType.HIGH_VALUE,"High value amount detected "+ transaction.getAmount());
        }
        if (transaction.getStatus()== TransactionStatus.FAILED){
            save(userId,AlertType.NEGATIVE_ATTEMPT, "You don't have enough money "+
                    transaction.getAmount() + " RON > " + balance + " RON)");
        }

        LocalDateTime oneMinuteAgo= LocalDateTime.now().minusMinutes(1);
        Long recentTransaction= 0L;
        if(transaction.getFromUser()!=null){
            recentTransaction= transactionRepository.countByFromUser_IdAndTimeAfter(userId,oneMinuteAgo);
        }else if(transaction.getToUser()!=null) {
            recentTransaction= transactionRepository.countByToUser_IdAndTimeAfter(userId,oneMinuteAgo);
        }
        if(recentTransaction>5){
            save(userId,AlertType.SUSPICIOUS_ACTIVITY,"Too many transactions made in the last minute");
        }
    }

    @Override
    public void save(Long userId, AlertType type, String message) {
        Alert alert= new Alert();
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        alert.setUser(user);
        alert.setType(type);
        alert.setMessage(message);
        alert.setTimestamp(LocalDateTime.now());
        alertRepository.save(alert);
    }

    @Override
    public List<AlertDto> findAll() {
        List<Alert> alertsList=alertRepository.findAll();
        List<AlertDto> alertsDtoList= alertsList.stream()
                .map(alertMapper::toDto)
                .toList();
        return alertsDtoList;
    }

    @Override
    public List<AlertDto> findByUserId(Long userId) {
        List<Alert> alertsList=alertRepository.findByUser_Id(userId);
        List<AlertDto> alertsDtoList= alertsList.stream()
                .map(alertMapper::toDto)
                .toList();
        return alertsDtoList;
    }
}
