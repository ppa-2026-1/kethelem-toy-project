package com.example.demo;

import java.util.List;

public record NewTicket(
    String action,
    String object,
    String details,
    Integer creatorId,
    Integer assigneeId,
    List<String> observers
) {}
