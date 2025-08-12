package org.techabraao.api.contacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.techabraao.api.contacts.model.UsersModel;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UsersModel, UUID> {
    UserDetails findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UsersModel u LEFT JOIN FETCH u.contacts WHERE u.id = :id")
    Optional<UsersModel> findByIdWithContacts(UUID id);

}
