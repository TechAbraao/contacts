package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.docs.ContactsDocs;
import org.techabraao.api.contacts.dto.ContactsDTO;
import org.techabraao.api.contacts.dto.request.ContactsRequest;
import org.techabraao.api.contacts.dto.request.UpdateContactRequest;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.techabraao.api.contacts.dto.response.ContactsResponse;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.enums.Roles;
import org.techabraao.api.contacts.mappers.ContactsMapper;
import org.techabraao.api.contacts.services.ContactsServices;
import org.slf4j.LoggerFactory;
import org.techabraao.api.contacts.validators.FormatsValidators;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
@Tag(name = "Contacts", description = "Operations related to contacts")
public class ContactsController implements ContactsDocs {
    private final ContactsServices contactsServices;
    private final FormatsValidators formatsValidators;

    private final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody @Valid ContactsRequest contactsRequest, @AuthenticationPrincipal UsersEntity me) {
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
                        "Contact added successfully.",
                        userAdded
                ));
    }

    @GetMapping
    public ResponseEntity<?> getAllContactsByUserId(@AuthenticationPrincipal UsersEntity me) {
        UUID userId = me.getId();
        Roles userRole = me.getRoles();
        List<ContactsResponse> allContacts;

        logger.info("Authenticated user has User ID equal to: '{}'.", userId);
        if (userRole.equals(Roles.ADMIN)) {
            logger.info("Searching all contacts through the administrator user. Your role is: {}", userRole);
            allContacts = contactsServices.allContacts();
        } else {
            logger.info("Searching all contacts through the basic user. Your role is: {} ", userRole);
            allContacts = contactsServices.allContactsByUserId(userId);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "All contacts were successfully found.",
                        allContacts
                ));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContactsByUserId(@AuthenticationPrincipal UsersEntity me, @PathVariable String contactId) {
        UUID idContact = formatsValidators.validateUUID(contactId);
        UUID userId = me.getId();
        Roles userRoles = me.getRoles();

        if (userRoles.equals(Roles.ADMIN)) {
            logger.info("Deleting contact by ID. Your role is: {}", userRoles);
            contactsServices.deleteContact(idContact);
        } else {
            logger.info("Deleting contact by ID. Your role is: {}", userRoles);
            contactsServices.deleteContactById(idContact, userId);
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContactById(@AuthenticationPrincipal UsersEntity me, @PathVariable String contactId) {
        UUID idContact = formatsValidators.validateUUID(contactId);
        logger.info("UUID successfully validated (String => UUID): '{}'.", contactId);

        UUID userId = me.getId();
        logger.info("Authenticated user has User ID equal to: '{}'.", userId);

        ContactsResponse contact = contactsServices.findById(idContact, userId);
        logger.info("Contact with id '{}' was successfully returned. Is: '{}'.", contactId, contact);

        return ResponseEntity
                .ok()
                .body(ApiResponse.success(
                        "Contact successfully found.",
                        contact
                ));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<?> updateContactByUserId(@PathVariable String contactId, @RequestBody UpdateContactRequest request, @AuthenticationPrincipal UsersEntity me) {
        UUID userId = me.getId();

        UUID id = formatsValidators.validateUUID(contactId);

        ContactsResponse contactChanged = contactsServices.updateContactByUserId(id, request, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
                "Contact information successfully changed.",
                contactChanged
        ));
    }
}
