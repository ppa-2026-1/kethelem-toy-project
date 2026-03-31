package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class TicketRepository {

    private final EntityManager em;

    public TicketRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Ticket> findById(Integer id) {
        return Optional.ofNullable(em.find(Ticket.class, id));
    }

    public List<Ticket> findAll() {
        return em.createQuery("FROM Ticket t", Ticket.class).getResultList();
    }

    @Transactional
    public void save(Ticket ticket) {
        if (ticket.getId() == null) {
            em.persist(ticket);
        } else {
            em.merge(ticket);
        }
    }
}
