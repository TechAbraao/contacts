package org.techabraao.api.contacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techabraao.api.contacts.model.ContactsModel;
import org.techabraao.api.contacts.model.UsersModel;

import java.util.Optional;
import java.util.UUID;

public interface ContactsRepository extends JpaRepository<ContactsModel, UUID> {
    boolean existsByEmailAndPhone(String email, Long phone);
    boolean existsByPhone(Long phone);
    boolean existsByEmail(String email);
}
