package org.techabraao.api.contacts.mappers;

import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.dto.response.UsersResponse;
import org.techabraao.api.contacts.entity.UsersEntity;

public class UsersMapper {

    // Entity to Response (Entity -> Response)
    public static UsersResponse toResponse(UsersEntity entity) {
        return new UsersResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRoles()
        );
    }

    // Request to Entity (Request -> Entity)
    public static UsersEntity toEntity(SignUpRequest request, String hashPassword) {
        UsersEntity user = new UsersEntity();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(hashPassword); // Password Hash inserted here.
        user.setRoles(request.role());

        return user;
    };



}
