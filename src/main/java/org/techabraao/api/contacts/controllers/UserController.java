package org.techabraao.api.contacts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.techabraao.api.contacts.dto.response.UserInfoDTO;
import org.techabraao.api.contacts.exceptions.UserNotFound;
import org.techabraao.api.contacts.model.UsersModel;
import org.techabraao.api.contacts.services.UserServices;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me")
public class UserController {

    private final UserServices userServices;

    /* Endpoint para obter informações do usuário autenticado */
    @GetMapping
    public ResponseEntity<?> getInfos(@AuthenticationPrincipal UsersModel user){
        UUID userId = user.getId();

        UsersModel userFound = userServices.searchUserById(userId);
        if (userFound == null) throw new UserNotFound("User not found for some reason.");

         UserInfoDTO userInfo = new UserInfoDTO(user);

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ApiResponse.success(HttpStatus.FOUND.value(), "User found successfully.", userInfo));
    }
}
