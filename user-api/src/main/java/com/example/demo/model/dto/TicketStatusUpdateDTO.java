package com.example.demo.model.dto;

public record TicketStatusUpdateDTO(
    String status,
    String reason,
    Integer responsibleId
) {}
