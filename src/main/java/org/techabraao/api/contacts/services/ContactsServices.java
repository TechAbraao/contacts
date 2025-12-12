package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.controllers.ContactsController;
import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.entity.ContactsEntity;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.mappers.ContactsMapper;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactsServices {
    private final ContactsRepository contactsRepository;
    private final UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(ContactsServices.class);

    public boolean verifyEmailOrPhoneExists(ContactsDTO contact, UUID userId) {
        boolean exists = contactsRepository.existsByEmailAndPhoneAndUser_Id(contact.email(), contact.phone(), userId);
        return exists;
    }

    public ContactsDTO addContact(ContactsDTO dto, UUID userId) {

        var user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        var entity = ContactsMapper.toEntity(dto);
        entity.setUser(user);

        ContactsDTO contacts = ContactsMapper.toDTO(contactsRepository.save(entity));

        return contacts;
    }

    public List<ContactsDTO> allContactsByUserId(UUID userId) {
        List<ContactsEntity> contacts =
                contactsRepository.findAllByUserId(userId);


        return contacts.stream()
                .map(ContactsMapper::toDTO)
                .toList();
    }

}
