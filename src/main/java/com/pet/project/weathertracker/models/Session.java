package com.pet.project.weathertracker.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    private UUID id;

    @OneToOne
    private User user;

    private LocalDateTime expiresAt;

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }

    public Session(User user) {
        id = UUID.randomUUID();
        this.user = user;
        expiresAt = LocalDateTime.now().plusDays(1);
    }
}
