package com.example.PayPlatform.model;

import com.example.PayPlatform.model.enums.AlertType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private AlertType type;
    private String message;
    @CreationTimestamp
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
