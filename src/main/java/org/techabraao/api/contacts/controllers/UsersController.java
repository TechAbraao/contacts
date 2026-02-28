package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.openapi.UsersOpenAPI;
import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.dto.request.UpdateUserRequest;
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
@Tag(name = "Users", description = "User-related operations.")
public class UsersController implements UsersOpenAPI {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<?> getInfosAboutMe(@AuthenticationPrincipal UsersEntity user) {
        UUID userId = user.getId();

        UsersResponse userFound = userServices.searchUserById(userId);
        if (userFound == null) throw new UserNotFoundException("User not found for some reason.");

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ApiResponse.success("User found successfully.", userFound));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {

        List<UsersResponse> allUsers = userServices.allUsers(username, email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "All Users successfully.",
                        allUsers));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(
            @PathVariable UUID userId
    ) {
        UUID uuid = UUID.fromString(userId.toString());
        var userFound = userServices.searchUserById(uuid);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "User successfully found.",
                        userFound
                ));
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody @Valid SignUpRequest request
    ) {
        UsersResponse userSaved = userServices.addUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(
            @PathVariable UUID userId
    ) {

        UUID uuid = UUID.fromString(userId.toString());
        var userDeleted = userServices.deleteUserById(uuid);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> changeUserById(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserRequest request
    ) {

        var userUpdated = userServices.userChanged(userId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User successfully updated.",
                        userUpdated
                )
        );
    }

}
