package com.example.PayPlatform.service.impl;

import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.User;
import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import com.example.PayPlatform.model.enums.TransactionStatus;
import com.example.PayPlatform.model.enums.TransactionType;
import com.example.PayPlatform.repository.TransactionRepository;
import com.example.PayPlatform.repository.UserRepository;
import com.example.PayPlatform.service.TransactionService;
import com.example.PayPlatform.service.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional
@Service

public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionMapper transactionMapper, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
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
                    transactionRepository.save(transaction);
                    throw new RuntimeException("Insufficient funds");
                }
                transaction.getFromUser().setBalance(transaction.getFromUser().getBalance().subtract(transaction.getAmount()));
                transaction.getToUser().setBalance(transaction.getToUser().getBalance().add(transaction.getAmount()));
                transaction.setStatus(TransactionStatus.SUCCESS);
                break;

            case WITHDRAW:
                if(transaction.getFromUser().getBalance().compareTo(transaction.getAmount())<0) {
                    transaction.setStatus(TransactionStatus.FAILED);
                    transactionRepository.save(transaction);
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
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
