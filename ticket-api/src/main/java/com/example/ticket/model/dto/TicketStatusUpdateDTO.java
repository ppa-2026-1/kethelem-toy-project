package com.example.ticket.model.dto;

public record TicketStatusUpdateDTO(
    String status,
    String reason,
    String responsibleEmail
) {}
