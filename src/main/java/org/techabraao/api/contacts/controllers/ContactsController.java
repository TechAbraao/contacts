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
import org.techabraao.api.contacts.dto.response.ContactsResponse;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.mappers.ContactsMapper;
import org.techabraao.api.contacts.repository.ContactsRepository;
import org.techabraao.api.contacts.services.ContactsServices;
import org.slf4j.LoggerFactory;
import org.techabraao.api.contacts.validators.ContactsValidators;

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
            (
                    @RequestBody @Valid ContactsRequest contactsRequest,
                    @AuthenticationPrincipal UsersEntity me) {
        UUID userId = me.getId();
        logger.info("Contact request received (ContactRequest): fullName - {}, phone - {}, email: {}",
                contactsRequest.fullname(),
                contactsRequest.phone(),
                contactsRequest.email());

        ContactsDTO contactsDTO = ContactsMapper.toDTO(contactsRequest);
        logger.info("ContactRequest to ContactDTO =>  {}", contactsDTO);

        ContactsDTO userAdded = contactsServices.addContact(contactsDTO, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Contact added successfully.",
                        userAdded
                ));
    }

    @GetMapping
    @Operation(summary = "Get all Contacts by User ID", description = "....")
    public ResponseEntity<?> getAllContactsByUserId(@AuthenticationPrincipal UsersEntity me) {
        UUID userId = me.getId();
        logger.info("Authenticated user has User ID equal to: '{}'.", userId);

        List<ContactsResponse> allContacts = contactsServices.allContactsByUserId(userId);
        logger.info("All contacts have been returned. {}", allContacts);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        HttpStatus.OK.value(),
                        "All contacts were successfully found.",
                        allContacts
                ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "....", description = "....")
    public ResponseEntity<?> deleteContactsByUserId
            (
                    @AuthenticationPrincipal UsersEntity me,
                    @PathVariable UUID id
            ) {
        UUID userId = me.getId();
        contactsServices.deleteContactById(id, userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
