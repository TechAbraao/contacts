package org.techabraao.api.contacts.dto.response;

import java.util.UUID;

public record ContactsResponse(
        UUID id,
        String fullName,
        Long phone,
        String email
) {
}
