package org.techabraao.api.contacts.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.techabraao.api.contacts.openapi.AuthorizationsOpenAPI;
import org.techabraao.api.contacts.dto.request.SignInRequest;
import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.dto.response.ApiResponse;
import org.techabraao.api.contacts.entity.RefreshTokensEntity;
import org.techabraao.api.contacts.exceptions.ContactAlreadyExistsException;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.mappers.TokensMapper;
import org.techabraao.api.contacts.services.TokenServices;
import org.techabraao.api.contacts.services.UserServices;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authorizations", description = "Operations related to authorization and authentication with Token JWT")
public class AuthorizationController implements AuthorizationsOpenAPI {

    private final UserServices userServices;
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;

    private String getClientIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
    private String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @RequestBody @Valid SignUpRequest credentials
    ) {

        if (userServices.verifyExistsUserByUsername(credentials)) {
            throw new ContactAlreadyExistsException("Email or username already in use.");
        };

        userServices.addUser(credentials);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "User created successfully.",
                        null
                ));
    };

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @RequestBody @Valid SignInRequest request, HttpServletRequest servletRequest
    ) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenServices.generateAccessToken((UsersEntity) auth.getPrincipal(), 5);
            var refreshToken = tokenServices.generateRefreshToken((UsersEntity) auth.getPrincipal(), 60 * 24 * 7);
            UUID userId = ((UsersEntity) auth.getPrincipal()).getId();

            String ip = getClientIpAddress(servletRequest);
            String userAgent = getUserAgent(servletRequest);
            var addedRefreshToken = tokenServices.addRefreshToken(refreshToken, userId, ip, userAgent);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new TokensMapper
                            .RefreshAndAccessTokenResponse(
                            LocalDateTime.now().toString(),
                            token,
                            refreshToken,
                            "Bearer"
                    )
            );

        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException("Username not found.");
        } catch (BadCredentialsException exception) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(
                            "Invalid credentials. Please try using different credentials."
                    ));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> signOut(
            @RequestBody TokensMapper.RefreshTokenRequest refreshTokenRequest
    ) {
        String refreshToken = refreshTokenRequest.refreshToken();
        RefreshTokensEntity revokedRefreshToken = tokenServices.rotateRefreshToken(refreshToken);

        Map<String, String> response = Map.of(
                "message", "Logged out successfully."
        );

        return  ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody @Valid TokensMapper.RefreshTokenRequest request, HttpServletRequest servletRequest
    ) {
        RefreshTokensEntity revokedRefreshToken = tokenServices.rotateRefreshToken(request.refreshToken());
        UsersEntity currentUser = revokedRefreshToken.getUser();

        var newToken = tokenServices.generateAccessToken(currentUser, 15);
        var newRefreshToken = tokenServices.generateRefreshToken(currentUser, 60 * 24 * 7);

        String ip = getClientIpAddress(servletRequest);
        String userAgent = getUserAgent(servletRequest);
        var addedRefreshToken = tokenServices.addRefreshToken(newRefreshToken, currentUser.getId(), ip, userAgent);

        return ResponseEntity.status(HttpStatus.OK).body(
                new TokensMapper.RefreshAndAccessTokenResponse(
                        LocalDateTime.now().toString(),
                        newToken,
                        newRefreshToken,
                        "Bearer"
                )
        );
    }
}
