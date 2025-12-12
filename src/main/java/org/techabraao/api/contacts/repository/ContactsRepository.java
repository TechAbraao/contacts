package org.techabraao.api.contacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techabraao.api.contacts.entity.ContactsEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactsRepository extends JpaRepository<ContactsEntity, UUID> {

    boolean existsByEmail(String email);
    boolean existsByPhone(Long phone);
    boolean existsByEmailAndPhoneAndUser_Id(String email, Long phone, UUID userId);

    Optional<ContactsEntity> findByEmailAndPhone(String email, Long phone);

    List<ContactsEntity> findAllByUserId(UUID userId);
}
