package org.techabraao.api.contacts.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.dto.request.UpdateUserRequest;
import org.techabraao.api.contacts.entity.UsersEntity;

import java.util.UUID;

public interface UsersOpenAPI {
    @Operation(
            summary = "Get User Infos.",
            description = "Get information from the authenticated user.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "basicAuth")})
    public ResponseEntity<?> getInfosAboutMe(@AuthenticationPrincipal UsersEntity user);


    @Operation(
            summary = "Get All Users.",
            description = "Get the data list for all users.",
            security = {
                    @SecurityRequirement(name = "basicAuth")
            })
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    );

    @Operation(
            summary = "Get User By ID.",
            description = "Get user information using their ID.",
            security = {@SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> getUserById(
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Create User.",
            description = "Create a new user.",
            security = {@SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> createUser(
            @RequestBody @Valid SignUpRequest request
    );

    @Operation(
            summary = "Delete User By ID.",
            description = "Delete a user by ID.",
            security = {@SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> deleteUser(
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Change User By ID.",
            description = "Change user information using their ID.",
            security = {@SecurityRequirement(name = "basicAuth")}
    )
    public ResponseEntity<?> changeUserById(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserRequest request
    );
}
