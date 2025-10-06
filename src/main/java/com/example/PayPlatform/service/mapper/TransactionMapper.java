package com.example.PayPlatform.service.mapper;

import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;

public class TransactionMapper {

    public Transaction fromDto(TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction=new Transaction();
        transaction.setType(transactionDtoRequest.getType());
        transaction.setIban(transactionDtoRequest.getIban());
        transaction.setAmount(transactionDtoRequest.getAmount());
        transaction.setFromUser(transactionDtoRequest.getFromUser());
        transaction.setToUser(transactionDtoRequest.getToUser());
        return transaction;
    }

    public TransactionDtoResponse toDto (Transaction  transaction) {
        TransactionDtoResponse transactionDtoResponse=new TransactionDtoResponse();
        transactionDtoResponse.setId(transaction.getId());
        transactionDtoResponse.setType(transaction.getType());
        transactionDtoResponse.setAmount(transaction.getAmount());
        transactionDtoResponse.setStatus(transaction.getStatus());
        transactionDtoResponse.setTime(transaction.getTime());
        return transactionDtoResponse;
    }
}
