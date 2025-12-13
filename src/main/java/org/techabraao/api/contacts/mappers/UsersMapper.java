package org.techabraao.api.contacts.mappers;

import org.techabraao.api.contacts.dto.response.UsersResponse;
import org.techabraao.api.contacts.entity.UsersEntity;

public class UsersMapper {
    public static UsersResponse toResponse(UsersEntity entity) {
        return new UsersResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRoles()
        );
    }
}
