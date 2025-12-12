package org.techabraao.api.contacts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.techabraao.api.contacts.entity.ContactsEntity;

public record ContactsDTO(
        @NotBlank(message = "Fullname is required.")
        String fullName,
        @NotNull(message = "Phone number is required.")
        Long phone,
        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        String email
) {
    public ContactsEntity toContactModel() {
        ContactsEntity contact = new ContactsEntity();
        contact.setFullName(fullName);
        contact.setPhone(phone);
        contact.setEmail(email);
        return contact;
    }
}
