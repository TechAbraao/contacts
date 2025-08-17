package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.dto.request.SignInDTO;
import org.techabraao.api.contacts.dto.request.SignUpDTO;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.techabraao.api.contacts.exceptions.DuplicateDataException;
import org.techabraao.api.contacts.entity.UsersModel;
import org.techabraao.api.contacts.services.TokenServices;
import org.techabraao.api.contacts.services.UserServices;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
@Tag(name = "Authorization", description = "Operations related to authorization and authentication with Token JWT")
public class AuthorizationController {

    private final UserServices userServices;
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;

    @PostMapping("/signup")
    @Operation(summary = "Sign Up", description = "Create a new User")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO credentials) {

        if (userServices.verifyExistsUserByUsername(credentials)) {
            throw new DuplicateDataException("Email or username already in use.");
        };

        userServices.addUser(credentials);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "User created successfully.",
                        null
                ));
    };

    @Operation(summary = "Sign In", description = "Login and authenticate user")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDTO credentials) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenServices.generateToken((UsersModel) auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(HttpStatus.OK.value(), "Token created successfully", token));
        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException("Username not found.");
        } catch (BadCredentialsException exception) {
            return ResponseEntity.notFound().build();
        }
    };


    /* Endpoint para sair.
    @PostMapping("/signout")
    public ResponseEntity<?> signOut(){};
    */

}
