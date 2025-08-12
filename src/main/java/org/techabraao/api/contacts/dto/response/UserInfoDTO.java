package org.techabraao.api.contacts.dto.response;

import org.techabraao.api.contacts.enums.Roles;
import org.techabraao.api.contacts.model.ContactsModel;

import java.util.List;

public record UserInfoDTO(
        String username,
        String email,
        Roles roles
) {
    public UserInfoDTO(org.techabraao.api.contacts.model.UsersModel user) {
        this(
            user.getUsername(),
            user.getEmail(),
            user.getRoles()
        );
    }
}
