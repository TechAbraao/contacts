package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techabraao.api.contacts.dto.request.ContactDTO;
import org.techabraao.api.contacts.exceptions.DuplicateDataException;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.services.ContactsServices;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
public class ContactsController {
    private final ContactsRepository contactsRepository;
    private final ContactsServices contactsServices;

    /* Endpoint para adicionar um novo contato */
    @PostMapping
    @Operation(summary = "Add a new Contact", description = "This endpoint will add a new user")
    public ResponseEntity<?> addContact(@RequestBody @Valid ContactDTO contact){

        Boolean checkingExists = contactsServices.verifyEmailOrPhoneExists(contact);
        if (checkingExists) throw new DuplicateDataException("Email and/or phone number are already in the database.");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
