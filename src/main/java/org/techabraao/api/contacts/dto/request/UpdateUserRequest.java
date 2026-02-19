package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserRequest(
        @Schema(description = "User name", example = "")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
        String username,

        @Schema(description = "User email", example = "")
        @Email(message = "Invalid e-mail format.")
        String email,

        @Schema(description = "New password", example = "")
        @Size(min = 4, max = 15, message = "Enter a password that is between 4 and 15 characters long.")
        String password
) {}
