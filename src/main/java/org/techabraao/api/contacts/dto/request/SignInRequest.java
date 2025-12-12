package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank(message = "This field is required.")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Characters containing accents and spaces are invalid.")
        String username,

        @NotBlank(message = "This field is required.")
        @Size(min = 4, max = 15, message = "Enter a password that is between 4 and 15 characters long.")
        String password
) {
}
