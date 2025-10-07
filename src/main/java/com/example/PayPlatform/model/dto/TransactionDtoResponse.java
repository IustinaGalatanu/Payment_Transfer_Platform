package com.example.PayPlatform.model.dto;

import com.example.PayPlatform.model.enums.TransactionStatus;
import com.example.PayPlatform.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDtoResponse {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private TransactionStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm", timezone = "Europe/Bucharest")
    private LocalDateTime time;
    private String iban;
    private Long fromUserId;
    private Long toUserId;

}
