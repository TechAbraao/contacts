package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.techabraao.api.contacts.entity.UsersModel;
import org.techabraao.api.contacts.services.UserServices;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me")
@Tag(name = "Me (Authenticated user)", description = "Operations related to the authenticated user")
public class UserController {

    private final UserServices userServices;


    @GetMapping
    @Operation(summary = "Get User Infos", description = "Get information from the authenticated user")
    public ResponseEntity<?> getInfos(@AuthenticationPrincipal UsersModel user){
        UUID userId = user.getId();

        UsersModel userFound = userServices.searchUserById(userId);
        if (userFound == null) throw new UserNotFound("User not found for some reason.");

         UserInfoDTO userInfo = new UserInfoDTO(user);

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(ApiResponse.success(HttpStatus.FOUND.value(), "User found successfully.", userInfo));
    }
}
