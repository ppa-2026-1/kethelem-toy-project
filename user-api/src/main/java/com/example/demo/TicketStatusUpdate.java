package com.example.demo;

public record TicketStatusUpdate(
    String status,
    String reason,
    Integer responsibleId
) {}
