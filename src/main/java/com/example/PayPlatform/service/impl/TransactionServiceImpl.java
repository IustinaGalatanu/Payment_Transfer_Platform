package com.example.PayPlatform.service.impl;

import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.User;
import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import com.example.PayPlatform.model.enums.TransactionStatus;
import com.example.PayPlatform.model.enums.TransactionType;
import com.example.PayPlatform.repository.TransactionRepository;
import com.example.PayPlatform.repository.UserRepository;
import com.example.PayPlatform.service.AlertService;
import com.example.PayPlatform.service.TransactionService;
import com.example.PayPlatform.service.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service

public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AlertService alertService;

    public TransactionServiceImpl(TransactionMapper transactionMapper, TransactionRepository transactionRepository, UserRepository userRepository, AlertService alertService) {
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.alertService = alertService;
    }

    @Override
    public TransactionDtoResponse save(TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction=transactionMapper.fromDto(transactionDtoRequest);
       if(transaction.getType() == TransactionType.TRANSFER){
           if(transactionDtoRequest.getFromUserId()!=null){
               User fromUser= userRepository.findById(transactionDtoRequest.getFromUserId())
                       .orElseThrow(() -> new RuntimeException("User not found"));
               transaction.setFromUser(fromUser);
           }
           if(transactionDtoRequest.getToUserId()!=null) {
               User toUser = userRepository.findById(transactionDtoRequest.getToUserId())
                       .orElseThrow(() -> new RuntimeException("User not found"));
               transaction.setToUser(toUser);
           }
       }else if(transaction.getType() == TransactionType.WITHDRAW ){
           if(transactionDtoRequest.getFromUserId()!=null){
               User fromUser= userRepository.findById(transactionDtoRequest.getFromUserId())
                       .orElseThrow(() -> new RuntimeException("User not found"));
               transaction.setFromUser(fromUser);
           }
           if (transactionDtoRequest.getIban()!=null) {
               transaction.setIban(transactionDtoRequest.getIban());
           }else{
               throw new RuntimeException("Iban required for withdraw") ;
           }
       }else if(transaction.getType() == TransactionType.DEPOSIT ) {
           if(transactionDtoRequest.getToUserId()!=null) {
               User toUser = userRepository.findById(transactionDtoRequest.getToUserId())
                       .orElseThrow(() -> new RuntimeException("User not found"));
               transaction.setToUser(toUser);
           }
           if (transactionDtoRequest.getIban()!=null) {
               transaction.setIban(transactionDtoRequest.getIban());
           }else{
               throw new RuntimeException("Iban required for withdraw") ;
           }
       }

        if(transaction.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Amount must be > 0");
        }

        switch (transaction.getType()) {
            case DEPOSIT :
                transaction.getToUser().setBalance(transaction.getToUser().getBalance().add(transaction.getAmount()));
                transaction.setStatus(TransactionStatus.SUCCESS);
                break;

            case TRANSFER:
                if (transaction.getFromUser().getBalance().compareTo(transaction.getAmount())<0){
                    transaction.setStatus(TransactionStatus.FAILED);
                    Transaction savedTransaction=transactionRepository.save(transaction);
                    alertService.checkForAlerts(savedTransaction,savedTransaction.getFromUser().getBalance());
                    throw new RuntimeException("Insufficient funds");

                }
                transaction.getFromUser().setBalance(transaction.getFromUser().getBalance().subtract(transaction.getAmount()));
                transaction.getToUser().setBalance(transaction.getToUser().getBalance().add(transaction.getAmount()));
                transaction.setStatus(TransactionStatus.SUCCESS);
                break;

            case WITHDRAW:
                if(transaction.getFromUser().getBalance().compareTo(transaction.getAmount())<0) {
                    transaction.setStatus(TransactionStatus.FAILED);
                    Transaction savedTransaction=transactionRepository.save(transaction);
                    alertService.checkForAlerts(savedTransaction,savedTransaction.getFromUser().getBalance());
                    throw new RuntimeException("Insufficient funds");
                }
                transaction.getFromUser().setBalance(transaction.getFromUser().getBalance().subtract(transaction.getAmount()));
                transaction.setStatus(TransactionStatus.SUCCESS);
        }
        if(transaction.getFromUser()!=null){
            userRepository.save(transaction.getFromUser());
        }
        if(transaction.getToUser() != null) {
            userRepository.save(transaction.getToUser());
        }
        Transaction transactionSaved = transactionRepository.save(transaction);
        if(transactionSaved.getFromUser()!=null){
            BigDecimal balance = transactionSaved.getFromUser().getBalance();
            alertService.checkForAlerts(transactionSaved,balance);
        }
        if(transactionSaved.getToUser()!=null){
            BigDecimal balance = transactionSaved.getToUser().getBalance();
            alertService.checkForAlerts(transactionSaved,balance);
        }
        return transactionMapper.toDto(transactionSaved);
    }

    @Override
    public List<TransactionDtoResponse> findAll() {
        List<Transaction> transactionsList= transactionRepository.findAll();
        List<TransactionDtoResponse> transactionsDtoResponseList = transactionsList.stream()
                .map(transactionMapper::toDto)
                .toList();
        return transactionsDtoResponseList;
    }

    @Override
    public Optional<TransactionDtoResponse> findById(Long id) {
        Optional<Transaction> transactionOptional=transactionRepository.findById(id);
        Optional<TransactionDtoResponse> transactionDtoResponse=transactionOptional.map(transactionMapper::toDto);
        return transactionDtoResponse;
    }

    @Override
    public List<TransactionDtoResponse> findByUserId(Long id) {
        User user= userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Transaction> transactionsList = transactionRepository.findByFromUser_IdOrToUser_Id(id,id);
        List<TransactionDtoResponse> transactionsDtoResponseList = transactionsList.stream()
                .map(transactionMapper::toDto)
                .toList();
        return transactionsDtoResponseList;
    }

    @Override
    public List<TransactionDtoResponse> findByDateBetween(LocalDateTime startTime, LocalDateTime endTime) {
        List<Transaction> transactionsList = transactionRepository.findByTimeBetween(startTime,endTime);
        List<TransactionDtoResponse> transactionsDtoResponseList = transactionsList.stream()
                .map(transaction -> transactionMapper.toDto(transaction))
                .toList();
        return transactionsDtoResponseList;
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
