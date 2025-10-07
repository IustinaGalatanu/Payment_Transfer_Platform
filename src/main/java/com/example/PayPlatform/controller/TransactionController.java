package com.example.PayPlatform.controller;


import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import com.example.PayPlatform.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDtoResponse> createTransaction (@RequestBody TransactionDtoRequest transactionDtoRequest) {
        TransactionDtoResponse transactionDtoResponse=transactionService.save(transactionDtoRequest);
        return ResponseEntity.ok(transactionDtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDtoResponse>> getAll() {
        List<TransactionDtoResponse> transactionDtoResponseList= transactionService.findAll();
        return ResponseEntity.ok(transactionDtoResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TransactionDtoResponse>> getById(@PathVariable Long id) {
        Optional<TransactionDtoResponse> transactionDtoResponse=transactionService.findById(id);
        return ResponseEntity.ok(transactionDtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
