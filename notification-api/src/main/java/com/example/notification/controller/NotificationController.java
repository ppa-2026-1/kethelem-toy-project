package com.example.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification.model.NotificationDTO;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void notify(@RequestBody NotificationDTO dto) {
        dto.recipients().forEach(recipient ->
            log.info("NOTIFICAÇÃO para {}: [{}] {}", recipient, dto.subject(), dto.body())
        );
    }
}
