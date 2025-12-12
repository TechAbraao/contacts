package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.dto.request.ContactsRequest;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.exceptions.DuplicateDataException;
import org.techabraao.api.contacts.entity.ContactsEntity;
import org.techabraao.api.contacts.mappers.ContactsMapper;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.services.ContactsServices;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
@Tag(name = "Contacts", description = "Operations related to contacts")
public class ContactsController {
    private final ContactsRepository contactsRepository;
    private final ContactsServices contactsServices;

    private final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @PostMapping
    @Operation(summary = "Add a new Contact", description = "This endpoint will add a contact based on the authenticated user")
    public ResponseEntity<?> createContact
            (@RequestBody @Valid ContactsRequest contactsRequest,
             @AuthenticationPrincipal UsersEntity me) {
        UUID userId = me.getId();
        logger.info("Contact request received: fullName - {}, phone - {}, email: {}",
                contactsRequest.fullname(),
                contactsRequest.phone(),
                contactsRequest.email());


        boolean checkingExists = contactsServices
                .verifyEmailOrPhoneExists(ContactsMapper
                        .toDTO(contactsRequest), userId);

        if (checkingExists) {
            throw new DuplicateDataException("Email and/or phone number are already in the database.");
        }

        ContactsDTO userAdded = contactsServices.addContact(ContactsMapper
                        .toDTO(contactsRequest), userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all Contacts by User ID", description = "")
    public ResponseEntity<?> getAllContactsByUserId(@AuthenticationPrincipal UsersEntity me){
        UUID userId = me.getId();
        logger.info("Authenticated user has User ID equal to: '{}'.", userId);

        List<ContactsDTO> allContacts = contactsServices.allContactsByUserId(userId);
        logger.info("All contacts have been returned. {}", allContacts);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        HttpStatus.OK.value(),
                        "All contacts were successfully found.",
                        allContacts
                ));
    }

}
