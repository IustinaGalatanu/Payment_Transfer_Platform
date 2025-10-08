package com.example.PayPlatform.service.mapper;

import com.example.PayPlatform.model.Alert;
import com.example.PayPlatform.model.dto.AlertDto;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public Alert fromDto (AlertDto alertDto) {
        Alert alert=new Alert();
        alert.setType(alertDto.getType());
        alert.setMessage(alertDto.getMessage());
     return alert;
    }

    public AlertDto toDto (Alert alert) {
        AlertDto alertDto=new AlertDto();
        alertDto.setId(alert.getId());
        alertDto.setType(alert.getType());
        alertDto.setMessage(alert.getMessage());
        alertDto.setTimestamp(alert.getTimestamp());
        alertDto.setUserId(alert.getUser().getId());
        return alertDto;
    }
}
