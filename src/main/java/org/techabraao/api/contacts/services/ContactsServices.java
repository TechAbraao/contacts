package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.dto.request.ContactDTO;
import org.techabraao.api.contacts.repository.ContactsRepository;

@Service
@RequiredArgsConstructor
public class ContactsServices {
    private final ContactsRepository contactsRepository;

    public boolean verifyEmailOrPhoneExists(ContactDTO contact) {
        boolean emailExists = contactsRepository.existsByEmail(contact.email());
        boolean phoneExists = contactsRepository.existsByPhone(contact.phone());
        return emailExists || phoneExists;
    }
}
