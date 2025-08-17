package org.techabraao.api.contacts.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.techabraao.api.contacts.enums.Roles;
import org.techabraao.api.contacts.entity.UsersModel;

public record SignUpDTO(
    @NotBlank(message = "Username is required.")
    String username,

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    String email,

    @NotNull(message = "Role is required.")
    Roles role,

    @NotBlank(message = "Password is required.")
    String password

) {
    public UsersModel toUsersModel(String hashPassword) {
        UsersModel user = new UsersModel();
        user.setUsername(username);
        user.setPassword(hashPassword);
        user.setEmail(email);
        user.setRoles(role);

        return user;
    }
}