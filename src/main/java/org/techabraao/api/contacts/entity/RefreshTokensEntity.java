package org.techabraao.api.contacts.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Table(name = "refresh_tokens", schema = "public")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RefreshTokensEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @Column(name = "expiresAt", nullable = false)
    private Instant expiresAt;
}
