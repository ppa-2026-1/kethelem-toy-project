package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Token;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public String validateToken(String token) {
        return tokenRepository.findById(token)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(t -> t.getUser().getHandle())
                .orElse(null);
    }

    public String login(String username, String password) {
        var user = userRepository.findByHandle(username)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        var token = new Token(UUID.randomUUID().toString(), user, LocalDateTime.now().plusHours(8));
        tokenRepository.save(token);
        return token.getToken();
    }

    public void logout(String token) {
        tokenRepository.findById(token).ifPresent(tokenRepository::delete);
    }
}
