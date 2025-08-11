package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.techabraao.api.contacts.model.ContactsModel;

public record ContactDTO(
        @NotBlank(message = "Fullname is required.")
        String fullname,
        @NotNull(message = "Phone number is required.")
        Long phone,
        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        String email
) {
    public ContactsModel toContactModel() {
        ContactsModel contact = new ContactsModel();
        contact.setFullName(fullname);
        contact.setPhone(phone);
        contact.setEmail(email);

        return contact;
    }
}
