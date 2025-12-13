package org.techabraao.api.contacts.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.dto.response.ContactsResponse;
import org.techabraao.api.contacts.entity.ContactsEntity;
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

    @Transactional
    public void deleteContactById(UUID contactId, UUID userId) {
        // * Regra de Negócio: Um usuário só pode deletar os próprios contatos. * //
        ContactsEntity contact = contactsValidators.contactOwnersShip(contactId, userId);
        contactsRepository.delete(contact);
    }

    public ContactsResponse findById(UUID contactId, UUID userId) {
        // * Regra de Negócio: Um usuário só pode consultar seus próprios contatos. * //
        ContactsEntity contact = contactsValidators.contactOwnersShip(contactId, userId);
        return  ContactsMapper.toResponse(contact);
    };
}
