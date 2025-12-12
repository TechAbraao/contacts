package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techabraao.api.contacts.dto.ContactDTO;
import org.techabraao.api.contacts.entity.UsersModel;
import org.techabraao.api.contacts.exceptions.DuplicateDataException;
import org.techabraao.api.contacts.entity.ContactsModel;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.services.ContactsServices;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me/contacts")
@Tag(name = "Contacts", description = "Operations related to contacts")
public class ContactsController {
    private final ContactsRepository contactsRepository;
    private final ContactsServices contactsServices;

    @PostMapping
    @Operation(summary = "Add a new Contact", description = "This endpoint will add a contact based on the authenticated user")
    public ResponseEntity<?> createContact(@RequestBody @Valid ContactDTO contact, @AuthenticationPrincipal UsersModel me){
        UUID userId = me.getId();

        Boolean checkingExists = contactsServices.verifyEmailOrPhoneExists(contact);
        if (checkingExists) throw new DuplicateDataException("Email and/or phone number are already in the database.");

        ContactsModel userAdded = contactsServices.addContact(contact, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
