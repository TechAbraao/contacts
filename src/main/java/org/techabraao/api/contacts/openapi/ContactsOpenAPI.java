package org.techabraao.api.contacts.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.techabraao.api.contacts.dto.request.ContactsRequest;
import org.techabraao.api.contacts.dto.request.UpdateContactRequest;
import org.techabraao.api.contacts.entity.UsersEntity;

public interface ContactsOpenAPI {

    @Operation(
            summary = "Add Contact.",
            description = "Create a new contact in an authenticated user.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> createContact(
            @RequestBody @Valid ContactsRequest contactsRequest, @AuthenticationPrincipal UsersEntity me
    );

    @Operation(
            summary = "Get All Contacts.",
            description = "All contacts of an authenticated user.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> getAllContactsByUserId(
            @AuthenticationPrincipal UsersEntity me,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    );

    @Operation(
            summary = "Delete Contact by ID.",
            description = "Delete contact using authenticated user ID.",
            security = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "basicAuth")
            }
    )
    public ResponseEntity<?> deleteContactsByUserId(
            @AuthenticationPrincipal UsersEntity me, @PathVariable String contactId
    );

    @Operation(
            summary = "Contact By ID.",
            description = "Get contact information using your authenticated user ID.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> getContactById(@AuthenticationPrincipal UsersEntity me, @PathVariable String contactId);

    @Operation(
            summary = "Change Contact By ID.",
            description = "",
            security = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "basicAuth")
            }
    )
    public ResponseEntity<?> updateContactByUserId(
            @PathVariable String contactId, @RequestBody UpdateContactRequest request, @AuthenticationPrincipal UsersEntity me
    );

    @Operation(
            summary = "All Favorites Contacts.",
            description = "",
            security = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "basicAuth")
            }
    )
    public ResponseEntity<?> getAllFavoriteContacts(
         @AuthenticationPrincipal UsersEntity me
    );

    @Operation(
            summary = "Favorite Contact.",
            description = "",
            security = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "basicAuth")
            }
    )
    public ResponseEntity<?> patchFavoriteContact(
            @AuthenticationPrincipal UsersEntity me, @PathVariable String contactId
    );
}
