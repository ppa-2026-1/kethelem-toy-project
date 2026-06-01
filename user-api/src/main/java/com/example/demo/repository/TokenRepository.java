package com.example.demo.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Token;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class TokenRepository {

    private final EntityManager em;

    public TokenRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Token> findById(String token) {
        return Optional.ofNullable(em.find(Token.class, token));
    }

    @Transactional
    public void save(Token token) {
        em.persist(token);
    }

    @Transactional
    public void delete(Token token) {
        em.remove(em.contains(token) ? token : em.merge(token));
    }
}
