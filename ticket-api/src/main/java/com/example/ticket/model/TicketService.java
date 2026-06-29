package com.example.ticket.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.ticket.model.dto.NewTicketDTO;
import com.example.ticket.model.dto.TicketStatusUpdateDTO;
import com.example.ticket.repository.TicketRepository;
import com.example.ticket.repository.entity.Ticket;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RestClient notificationRestClient;

    public TicketService(TicketRepository ticketRepository, RestClient notificationRestClient) {
        this.ticketRepository = ticketRepository;
        this.notificationRestClient = notificationRestClient;
    }

    public void create(NewTicketDTO dto) {
        if (dto.action() == null || dto.action().isBlank())
            throw new IllegalArgumentException("action é obrigatório");
        if (dto.object() == null || dto.object().isBlank())
            throw new IllegalArgumentException("object é obrigatório");
        if (dto.creatorEmail() == null || dto.creatorEmail().isBlank())
            throw new IllegalArgumentException("creatorEmail é obrigatório");

        Ticket ticket = new Ticket();
        ticket.setAction(dto.action());
        ticket.setObject(dto.object());
        ticket.setDetails(dto.details());
        ticket.setCreatorEmail(dto.creatorEmail());
        ticket.setAssigneeEmail(dto.assigneeEmail() != null ? dto.assigneeEmail() : dto.creatorEmail());
        ticket.setObservers(dto.observers() != null ? dto.observers() : List.of());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        notifyInteressados(ticket);
    }

    public void updateStatus(Integer id, TicketStatusUpdateDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));

        if (dto.status() == null || dto.status().isBlank())
            throw new IllegalArgumentException("status é obrigatório");

        String newStatus = dto.status().toUpperCase();

        if (newStatus.equals("EM_ANDAMENTO")) {
            if (dto.responsibleEmail() == null || dto.responsibleEmail().isBlank())
                throw new IllegalArgumentException("responsibleEmail é obrigatório para colocar em andamento");
            ticket.setResponsibleEmail(dto.responsibleEmail());
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

    private void notifyInteressados(Ticket ticket) {
        List<String> recipients = new ArrayList<>();
        recipients.add(ticket.getCreatorEmail());
        if (!ticket.getAssigneeEmail().equals(ticket.getCreatorEmail()))
            recipients.add(ticket.getAssigneeEmail());
        if (ticket.getObservers() != null)
            recipients.addAll(ticket.getObservers());

        NotificationDTO notification = new NotificationDTO(
            "Novo ticket: " + ticket.getAction() + " " + ticket.getObject(),
            "Um novo ticket foi criado. Detalhes: " + ticket.getDetails(),
            recipients
        );

        notificationRestClient.post()
            .uri("/api/v1/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .body(notification)
            .retrieve()
            .toBodilessEntity();
    }
}
