package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TicketService;
import com.example.demo.model.dto.NewTicketDTO;
import com.example.demo.model.dto.TicketStatusUpdateDTO;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.entity.Ticket;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    public TicketController(TicketRepository ticketRepository, TicketService ticketService) {
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody NewTicketDTO dto) {
        ticketService.create(dto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> list() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> get(@PathVariable Integer id) {
        return ticketRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(value = "/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestBody TicketStatusUpdateDTO dto) {
        ticketService.updateStatus(id, dto);
        return ResponseEntity.noContent().build();
    }
}
