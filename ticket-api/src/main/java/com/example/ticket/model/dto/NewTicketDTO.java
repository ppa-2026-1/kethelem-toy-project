package com.example.ticket.model.dto;

import java.util.List;

public record NewTicketDTO(
    String action,
    String object,
    String details,
    String creatorEmail,
    String assigneeEmail,
    List<String> observers
) {}
