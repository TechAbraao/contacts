package org.techabraao.api.contacts.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateContactRequest(

        @Schema(description = "Full name.")
        String fullname,

        @Schema(description = "Phone number.")
        @NotNull(message = "Phone number is required.")
        Long phone,

        @Schema(description = "E-mail valid.")
        @Email(message = "Invalid email format.")
        String email
) {}
