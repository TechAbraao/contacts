package org.techabraao.api.contacts.docs;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.techabraao.api.contacts.dto.request.SignInRequest;
import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.mappers.TokensMapper;

public interface AuthorizationsDocs {

    @Operation(
            summary = "Sign Up.",
            description = "Create a new User"
    )
    public ResponseEntity<?> signUp(
            @RequestBody @Valid SignUpRequest credentials
    );

    @Operation(
            summary = "Sign In.",
            description = "Login and authenticate user"
    )
    public ResponseEntity<?> signIn(
            @RequestBody @Valid SignInRequest request, HttpServletRequest servletRequest
    ) throws Exception;

    @Operation(
            summary = "Logout.",
            description = ""
    )
    public ResponseEntity<?> signOut(
            @RequestBody TokensMapper.RefreshTokenRequest refreshTokenRequest
    );

    @Operation(
            summary = "Refresh Token.",
            description = "Update the expiration time of your access token."
    )
    public ResponseEntity<?> refreshToken(
            @RequestBody @Valid TokensMapper.RefreshTokenRequest request, HttpServletRequest servletRequest
    );
}
