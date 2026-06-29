package com.example.notification.model;

import java.util.List;

public record NotificationDTO(
    String subject,
    String body,
    List<String> recipients
) {}
