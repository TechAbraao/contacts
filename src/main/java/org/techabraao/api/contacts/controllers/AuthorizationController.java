package org.techabraao.api.contacts.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.dto.SignInDTO;
import org.techabraao.api.contacts.services.TokenServices;
import org.techabraao.api.contacts.services.UserServices;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
public class AuthorizationController {

    private final UserServices userServices;
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;

    /* Endpoint para registrar usuário. */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignInDTO credentials) throws Exception {
        try {

            return ResponseEntity.ok(credentials);

        } catch (Exception exception) {
            String messageException = exception.getMessage();
            throw new Exception("Error: " + messageException);
        }
    };

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignInDTO credentials) throws Exception {
        try {

            return ResponseEntity.ok(credentials);

        } catch (Exception exception) {
            String messageException = exception.getMessage();
            throw new Exception("Error: " + messageException);
        }
    };


    /* Endpoint para sair.
    @PostMapping("/signout")
    public ResponseEntity<?> signout(){};
    */

}
