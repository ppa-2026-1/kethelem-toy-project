package com.example.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket.repository.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {}
