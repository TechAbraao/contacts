package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContactsRequest(
        @NotBlank(message = "Fullname is required.")
        String fullname,
        @NotNull(message = "Phone number is required.")
        Long phone,
        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        String email
) {
}
