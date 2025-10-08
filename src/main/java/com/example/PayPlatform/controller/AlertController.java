package com.example.PayPlatform.controller;

import com.example.PayPlatform.model.dto.AlertDto;
import com.example.PayPlatform.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<List<AlertDto>> findAll () {
        List<AlertDto> alertsDtoList = alertService.findAll();
        return ResponseEntity.ok(alertsDtoList);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<List<AlertDto>> findByUserId (@PathVariable Long userId) {
        List<AlertDto> alertsDtoList = alertService.findByUserId(userId);
        return ResponseEntity.ok(alertsDtoList);
    }

}
