package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.dto.SignUpDTO;
import org.techabraao.api.contacts.dto.request.SignInRequest;
import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.techabraao.api.contacts.exceptions.ContactAlreadyExistsException;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.services.TokenServices;
import org.techabraao.api.contacts.services.UserServices;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authorizations", description = "Operations related to authorization and authentication with Token JWT")
public class AuthorizationController {

    private final UserServices userServices;
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;

    @PostMapping("/signup")
    @Operation(summary = "Sign Up", description = "Create a new User")
    public ResponseEntity<?> signUp(
            @RequestBody @Valid SignUpRequest credentials
    ) {

        if (userServices.verifyExistsUserByUsername(credentials)) {
            throw new ContactAlreadyExistsException("Email or username already in use.");
        };

        userServices.addUser(credentials);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(ApiResponse.success(
                        "User created successfully.",
                        null
                ));
    };

    @Operation(summary = "Sign In", description = "Login and authenticate user")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @RequestBody @Valid SignInRequest request
    ) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenServices.generateAccessToken((UsersEntity) auth.getPrincipal());
            var refreshToken = tokenServices.generateRefreshToken((UsersEntity) auth.getPrincipal());

            UUID userId = ((UsersEntity) auth.getPrincipal()).getId();

            var addRefreshToken = tokenServices.addRefreshToken(refreshToken, userId);

            Map<String, Object> body = Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "accessToken", token,
                    "refreshToken", refreshToken,
                    "type", "Bearer"
            );
            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException("Username not found.");
        } catch (BadCredentialsException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(
                            "Invalid credentials. Please try using different credentials."
                    ));
        }
    }

    @Operation(summary = "Sign Out", description = "")
    @PostMapping("/signout")
    public ResponseEntity<?> signOut() {
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Refresh Token", description = "")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
