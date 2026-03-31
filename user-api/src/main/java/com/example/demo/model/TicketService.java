package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.dto.NewTicketDTO;
import com.example.demo.model.dto.TicketStatusUpdateDTO;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Ticket;
import com.example.demo.repository.entity.User;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void create(NewTicketDTO dto) {
        if (dto.action() == null || dto.action().isBlank())
            throw new IllegalArgumentException("action é obrigatório");
        if (dto.object() == null || dto.object().isBlank())
            throw new IllegalArgumentException("object é obrigatório");
        if (dto.creatorId() == null)
            throw new IllegalArgumentException("creatorId é obrigatório");

        User creator = userRepository.findById(dto.creatorId())
            .orElseThrow(() -> new IllegalArgumentException("Criador não encontrado"));

        User assignee = dto.assigneeId() != null
            ? userRepository.findById(dto.assigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Destinatário não encontrado"))
            : creator;

        Ticket ticket = new Ticket();
        ticket.setAction(dto.action());
        ticket.setObject(dto.object());
        ticket.setDetails(dto.details());
        ticket.setCreator(creator);
        ticket.setAssignee(assignee);
        ticket.setObservers(dto.observers() != null ? dto.observers() : List.of());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }

    public void updateStatus(Integer id, TicketStatusUpdateDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));

        if (dto.status() == null || dto.status().isBlank())
            throw new IllegalArgumentException("status é obrigatório");

        String newStatus = dto.status().toUpperCase();

        if (newStatus.equals("EM_ANDAMENTO")) {
            if (dto.responsibleId() == null)
                throw new IllegalArgumentException("responsibleId é obrigatório para colocar em andamento");
            User responsible = userRepository.findById(dto.responsibleId())
                .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
            ticket.setResponsible(responsible);
        }

        if (newStatus.equals("CANCELADO")) {
            if (dto.reason() == null || dto.reason().isBlank())
                throw new IllegalArgumentException("reason é obrigatório para cancelar");
            ticket.setReason(dto.reason());
        }

        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
    }
}
