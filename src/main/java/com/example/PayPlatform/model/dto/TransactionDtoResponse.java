package com.example.PayPlatform.model.dto;

import com.example.PayPlatform.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDtoResponse {

    private Long id;
    private String type;
    private BigDecimal amount;
    private String status;
    private LocalDateTime time;

}
