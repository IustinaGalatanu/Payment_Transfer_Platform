package com.example.PayPlatform.model;

import com.example.PayPlatform.model.enums.TransactionStatus;
import com.example.PayPlatform.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table( name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TransactionType type;
    private String iban;
    private BigDecimal amount;
    private TransactionStatus status;
    @CreationTimestamp
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;
}
