package com.example.PayPlatform.model.dto;

import com.example.PayPlatform.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDtoRequest {

    private String type;
    private String iban;
    private BigDecimal amount;
    private User fromUser;
    private User toUser;
}
