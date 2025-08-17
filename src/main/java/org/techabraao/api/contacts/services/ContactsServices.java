package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.dto.request.ContactDTO;
import org.techabraao.api.contacts.entity.ContactsModel;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactsServices {
    private final ContactsRepository contactsRepository;
    private final UserRepository userRepository;

    public boolean verifyEmailOrPhoneExists(ContactDTO contact) {
        boolean emailExists = contactsRepository.existsByEmail(contact.email());
        boolean phoneExists = contactsRepository.existsByPhone(contact.phone());
        return emailExists || phoneExists;
    }

    public ContactsModel addContact(ContactDTO contact, UUID userId) {
        var authenticatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        ContactsModel contactEntity = contact.toContactModel();
        contactEntity.setUser(authenticatedUser);

        return contactsRepository.save(contactEntity);
    }
}
