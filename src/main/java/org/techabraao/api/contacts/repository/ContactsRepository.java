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
    List<ContactsEntity> findByFullNameContainingIgnoreCase(String fullName);
    List<ContactsEntity> findByEmailContainingIgnoreCase(String email);
    List<ContactsEntity> findByUserIdAndFullNameContainingIgnoreCase(UUID userId, String name);
    List<ContactsEntity> findByUserIdAndEmailContainingIgnoreCase(UUID userId, String email);
    List<ContactsEntity> findByUserIdAndIsFavoriteTrue(UUID userId);
    List<ContactsEntity> findByIsFavoriteTrue();

    Optional<ContactsEntity> findByIdAndUserId(UUID id, UUID userId);
    void deleteByIdAndUserId(UUID id, UUID userId);
}
