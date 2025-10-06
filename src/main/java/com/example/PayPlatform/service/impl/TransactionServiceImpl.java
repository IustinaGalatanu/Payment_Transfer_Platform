package com.example.PayPlatform.service.impl;

import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import com.example.PayPlatform.repository.TransactionRepository;
import com.example.PayPlatform.service.TransactionService;
import com.example.PayPlatform.service.mapper.TransactionMapper;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionMapper transactionMapper, TransactionRepository transactionRepository) {
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDtoResponse save(TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction=transactionMapper.fromDto(transactionDtoRequest);
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
