package com.example.PayPlatform.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String iban;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
