package org.techabraao.api.contacts.controllers;

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
import org.techabraao.api.contacts.model.UsersModel;
import org.techabraao.api.contacts.services.TokenServices;
import org.techabraao.api.contacts.services.UserServices;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
public class AuthorizationController {

    private final UserServices userServices;
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;

    /**
     * Endpoint para registrar um novo usuário no sistema.
     *
     * @param credentials Objeto {@link SignUpDTO} contendo os dados necessários para o cadastro do usuário.
     * @return {@link ResponseEntity} com status 201 (Created) se o usuário foi criado com sucesso.
     * @throws DuplicateDataException se o email ou username já estiverem cadastrados.
     */
    @PostMapping("/signup")
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
