package com.example.PayPlatform.model.dto;

import com.example.PayPlatform.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDtoRequest {

    private TransactionType type;
    private String iban;
    private BigDecimal amount;
    private Long fromUserId;
    private Long toUserId;
}
