package org.techabraao.api.contacts.mappers;

import jakarta.validation.constraints.NotBlank;

public class TokensMapper {
    public record RefreshTokenRequest(

            @NotBlank(message = "Refresh token is required.")
            String refreshToken

    ) {}

    public record RefreshAndAccessTokenResponse(

            String timestamp,
            String accessToken,
            String refreshToken,
            String type

    ) {}

    public record TokenResponse(

            String timestamp,
            String accessToken,
            String refreshToken,
            String type

    ) {}
}
