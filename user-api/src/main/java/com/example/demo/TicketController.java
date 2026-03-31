package com.example.demo;

import java.time.LocalDateTime;
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

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketController(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody NewTicket newTicket) {
        if (newTicket.action() == null || newTicket.action().isBlank())
            throw new IllegalArgumentException("action é obrigatório");
        if (newTicket.object() == null || newTicket.object().isBlank())
            throw new IllegalArgumentException("object é obrigatório");
        if (newTicket.creatorId() == null)
            throw new IllegalArgumentException("creatorId é obrigatório");

        User creator = userRepository.findById(newTicket.creatorId())
            .orElseThrow(() -> new IllegalArgumentException("Criador não encontrado"));

        User assignee = newTicket.assigneeId() != null
            ? userRepository.findById(newTicket.assigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Destinatário não encontrado"))
            : creator;

        Ticket ticket = new Ticket();
        ticket.setAction(newTicket.action());
        ticket.setObject(newTicket.object());
        ticket.setDetails(newTicket.details());
        ticket.setCreator(creator);
        ticket.setAssignee(assignee);
        ticket.setObservers(newTicket.observers() != null ? newTicket.observers() : List.of());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
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
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestBody TicketStatusUpdate update) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));

        if (update.status() == null || update.status().isBlank())
            throw new IllegalArgumentException("status é obrigatório");

        String newStatus = update.status().toUpperCase();

        if (newStatus.equals("EM_ANDAMENTO")) {
            if (update.responsibleId() == null)
                throw new IllegalArgumentException("responsibleId é obrigatório para colocar em andamento");
            User responsible = userRepository.findById(update.responsibleId())
                .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
            ticket.setResponsible(responsible);
        }

        if (newStatus.equals("CANCELADO")) {
            if (update.reason() == null || update.reason().isBlank())
                throw new IllegalArgumentException("reason é obrigatório para cancelar");
            ticket.setReason(update.reason());
        }

        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        return ResponseEntity.noContent().build();
    }
}
