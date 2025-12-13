package org.techabraao.api.contacts.dto.response;

import org.techabraao.api.contacts.enums.Roles;
import java.util.UUID;

public record UsersResponse(
        UUID id,
        String username,
        String email,
        Roles roles
) {
}
