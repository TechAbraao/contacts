package org.techabraao.api.contacts.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.dto.request.UpdateContactRequest;
import org.techabraao.api.contacts.dto.response.ContactsResponse;
import org.techabraao.api.contacts.entity.ContactsEntity;
import org.techabraao.api.contacts.enums.Roles;
import org.techabraao.api.contacts.exceptions.ContactNotFoundException;
import org.techabraao.api.contacts.mappers.ContactsMapper;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.repository.UsersRepository;
import org.techabraao.api.contacts.validators.ContactsValidators;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactsServices {
    private final ContactsRepository contactsRepository;
    private final UsersRepository usersRepository;
    private final ContactsValidators contactsValidators;

    private final Logger logger = LoggerFactory.getLogger(ContactsServices.class);

    public ContactsDTO addContact(ContactsDTO dto, UUID userId) {
        contactsValidators.validateContactDoesNotExist(dto, userId);
        var user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        var entity = ContactsMapper.toEntity(dto);
        entity.setUser(user);

        return ContactsMapper.toDTO(contactsRepository.save(entity));
    }

    public List<ContactsResponse> allContactsByUserId(UUID userId) {
        List<ContactsEntity> contacts =
                contactsRepository.findAllByUserId(userId);
        return contacts.stream()
                .map(ContactsMapper::toResponse)
                .toList();
    }

    public List<ContactsResponse> allContacts() {
        List<ContactsEntity> contacts = contactsRepository.findAll();
        return contacts.stream()
                .map(ContactsMapper::toResponse)
                .toList();
    }

    @Transactional
    public ContactsResponse updateContactByUserId(UUID contactId, UpdateContactRequest request, UUID userId) {

        // * Regra de Negócio: Um usuário só pode alterar seus próprios contatos * //
        var contactChanged = contactsValidators.contactOwnersShip(contactId, userId);
        if (request.fullname() != null) {
            contactChanged.setFullName(request.fullname());
        }

        if (request.phone() != null) {
            contactChanged.setPhone(request.phone());
        }

        if (request.email() != null) {
            contactChanged.setEmail(request.email());
        }

        return ContactsMapper.toResponse(contactChanged);
    }

    @Transactional
    public void deleteContactById(UUID contactId, UUID userId) {
        // * Regra de Negócio: Um usuário só pode deletar os próprios contatos. * //
        ContactsEntity contact = contactsValidators.contactOwnersShip(contactId, userId);
        contactsRepository.delete(contact);
    }

    @Transactional
    public void deleteContact(UUID contactId) {
        var contactExists = contactsRepository.existsById(contactId);
        if (contactExists) {
            contactsRepository.deleteById(contactId);
        } else {
            throw new ContactNotFoundException("Contact with id: " + contactId + " does not exist.");
        }
    }

    public ContactsResponse findById(UUID contactId, UUID userId) {
        // * Regra de Negócio: Um usuário só pode consultar seus próprios contatos. * //
        ContactsEntity contact = contactsValidators.contactOwnersShip(contactId, userId);
        return  ContactsMapper.toResponse(contact);
    };
}
