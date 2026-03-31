package com.example.demo.model.dto;

import java.util.List;

public record NewTicketDTO(
    String action,
    String object,
    String details,
    Integer creatorId,
    Integer assigneeId,
    List<String> observers
) {}
