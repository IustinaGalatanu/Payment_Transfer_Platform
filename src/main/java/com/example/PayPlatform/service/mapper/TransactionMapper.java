package com.example.PayPlatform.service.mapper;

import com.example.PayPlatform.model.Transaction;
import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction fromDto(TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction=new Transaction();
        transaction.setType(transactionDtoRequest.getType());
        transaction.setAmount(transactionDtoRequest.getAmount());
        return transaction;
    }

    public TransactionDtoResponse toDto (Transaction  transaction) {
        TransactionDtoResponse transactionDtoResponse=new TransactionDtoResponse();
        transactionDtoResponse.setId(transaction.getId());
        transactionDtoResponse.setType(transaction.getType());
        transactionDtoResponse.setAmount(transaction.getAmount());
        transactionDtoResponse.setStatus(transaction.getStatus());
        transactionDtoResponse.setTime(transaction.getTime());
        transactionDtoResponse.setFromUserId(transaction.getFromUser()!= null? transaction.getFromUser().getId():null);
        transactionDtoResponse.setToUserId(transaction.getToUser()!=null?transaction.getToUser().getId():null);
        transactionDtoResponse.setIban(transaction.getIban()!=null?transaction.getIban():null);
        return transactionDtoResponse;
    }
}
