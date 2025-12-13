package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.techabraao.api.contacts.dto.response.UsersResponse;
import org.techabraao.api.contacts.exceptions.UserNotFoundException;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.services.UserServices;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users Informations", description = "Obtain user data and/or your data.")
public class UsersController {
    private final UserServices userServices;

    @GetMapping("/me")
    @Operation(summary = "Get User Infos", description = "Get information from the authenticated user")
    public ResponseEntity<?> getInfosAboutMe(@AuthenticationPrincipal UsersEntity user) {
        UUID userId = user.getId();

        UsersResponse userFound = userServices.searchUserById(userId);
        if (userFound == null) throw new UserNotFoundException("User not found for some reason.");

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ApiResponse.success(HttpStatus.FOUND.value(), "User found successfully.", userFound));
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Get the data list for all users.")
    public ResponseEntity<?> getAllUsers() {
        List<UsersResponse> allUsers = userServices.allUsers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        HttpStatus.OK.value(),
                        "All Users successfully.",
                        allUsers));
    }
}
