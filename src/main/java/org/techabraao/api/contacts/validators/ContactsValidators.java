package org.techabraao.api.contacts.validators;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.entity.ContactsEntity;
import org.techabraao.api.contacts.exceptions.ContactAlreadyExistsException;
import org.techabraao.api.contacts.exceptions.ResourceNotFoundException;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.services.ContactsServices;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class ContactsValidators {
    private final ContactsRepository contactsRepository;

    private final Logger logger = LoggerFactory.getLogger(ContactsValidators.class);

    public void validateContactDoesNotExist(ContactsDTO contact, UUID userId) {
        //* Regra de Negócio: Verificar se as credenciais já existem na base de dados. *//

        boolean existsEmailAndPhone = contactsRepository.existsByEmailAndPhoneAndUser_Id(
                contact.email(),
                contact.phone(),
                userId
        );
        if (existsEmailAndPhone) {
            logger.warn(
                    "Attempt to create duplicated contact. email={}, phone={}, userId={}",
                    contact.email(),
                    contact.phone(),
                    userId
            );
            throw new ContactAlreadyExistsException("A contact with this email or phone already exists for this user.");
        }
    }

    public ContactsEntity contactOwnersShip(UUID contactId, UUID userId) {
        return contactsRepository
                .findByIdAndUserId(contactId, userId)
                .orElseThrow(() -> {
                    logger.warn(
                            "Contact ownership violation or not found. contactId={}, userId={}",
                            contactId,
                            userId
                    );
                    return new ResourceNotFoundException(
                            "Contact not found or does not belong to the user."
                    );
                });
    }

}
