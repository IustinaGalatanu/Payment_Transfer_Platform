package com.example.PayPlatform.model.dto;

import com.example.PayPlatform.model.User;
import com.example.PayPlatform.model.enums.AlertType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class AlertDto {

    private Long id;
    private AlertType type;
    private String message;
    private LocalDateTime timestamp;
    private Long userId;
}
