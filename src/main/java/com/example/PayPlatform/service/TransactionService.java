package com.example.PayPlatform.service;
import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import com.example.PayPlatform.model.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface TransactionService {

    public TransactionDtoResponse save (TransactionDtoRequest transactionDtoRequest);

    public List<TransactionDtoResponse> findAll ();

    public Optional<TransactionDtoResponse> findById (Long id);

    public void delete (Long id);
}
