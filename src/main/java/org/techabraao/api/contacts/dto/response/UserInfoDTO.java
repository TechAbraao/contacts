package org.techabraao.api.contacts.dto.response;

import org.techabraao.api.contacts.enums.Roles;

public record UserInfoDTO(
        String username,
        String email,
        Roles roles
) {
    public UserInfoDTO(org.techabraao.api.contacts.entity.UsersModel user) {
        this(
            user.getUsername(),
            user.getEmail(),
            user.getRoles()
        );
    }
}
