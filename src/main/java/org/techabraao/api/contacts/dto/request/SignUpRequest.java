package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.*;
import org.techabraao.api.contacts.enums.Roles;

public record SignUpRequest(
        @NotBlank(message = "Username is required.")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Characters containing accents and spaces are invalid.")
        String username,

        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        String email,

        @NotNull(message = "Role is required.")
        Roles role,

        @NotBlank(message = "Password is required.")
        @Size(min = 4, max = 15, message = "Enter a password that is between 4 and 15 characters long.")
        String password
) {
}
