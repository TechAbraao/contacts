package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInDTO(
        @NotBlank(message = "This field is required.")
        String username,
        @NotBlank(message = "This field is required.")
        String password
) {
}
