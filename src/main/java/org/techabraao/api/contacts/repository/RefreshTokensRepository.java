package org.techabraao.api.contacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techabraao.api.contacts.entity.RefreshTokensEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokensRepository extends JpaRepository<RefreshTokensEntity, UUID> {
    Optional<RefreshTokensEntity> findByToken(String token);
}
