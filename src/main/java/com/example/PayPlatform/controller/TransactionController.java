package com.example.PayPlatform.controller;


import com.example.PayPlatform.model.dto.TransactionDtoRequest;
import com.example.PayPlatform.model.dto.TransactionDtoResponse;
import com.example.PayPlatform.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        TransactionDtoResponse transactionsDtoResponse=transactionService.save(transactionDtoRequest);
        return ResponseEntity.ok(transactionsDtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDtoResponse>> getAll() {
        List<TransactionDtoResponse> transactionsDtoResponseList= transactionService.findAll();
        return ResponseEntity.ok(transactionsDtoResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TransactionDtoResponse>> getById(@PathVariable Long id) {
        Optional<TransactionDtoResponse> transactionsDtoResponse=transactionService.findById(id);
        return ResponseEntity.ok(transactionsDtoResponse);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TransactionDtoResponse>> getTransactionsByUserId (@PathVariable Long id) {
        List<TransactionDtoResponse> transactionsDtoResponseList =transactionService.findByUserId(id);
        return ResponseEntity.ok(transactionsDtoResponseList);
    }

    @GetMapping("/date")
    public ResponseEntity<List<TransactionDtoResponse>> getTransactionsByDateBetween (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
    {
        List<TransactionDtoResponse> transactionsDtoResponseList = transactionService.findByDateBetween(startTime,endTime);
        return ResponseEntity.ok(transactionsDtoResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
