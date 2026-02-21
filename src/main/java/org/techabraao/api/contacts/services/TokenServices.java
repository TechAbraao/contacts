package org.techabraao.api.contacts.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.entity.RefreshTokensEntity;
import org.techabraao.api.contacts.entity.UsersEntity;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.techabraao.api.contacts.exceptions.RefreshTokenExpiredException;
import org.techabraao.api.contacts.exceptions.RefreshTokenNotFoundException;
import org.techabraao.api.contacts.exceptions.RefreshTokenRevokedException;
import org.techabraao.api.contacts.repository.RefreshTokensRepository;
import org.techabraao.api.contacts.repository.UsersRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServices {
    // SECRETKEY TA HARDCODED, VERIFICAR ISSO FUTURAMENTE!!!
    // NAO PODE! USAR O @VALUE("${}") POSTERIORMENTE

    final static String issuer = "contactsAPI";
    private final UsersRepository usersRepository;
    private final RefreshTokensRepository refreshTokensRepository;

    private Instant generateExpirationDate(Integer minutes) {
        return LocalDateTime
                .now()
                .plusMinutes(minutes)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateAccessToken(UsersEntity user, Integer expirationMinutes) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRETKEY");

            String accessToken = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(generateExpirationDate(expirationMinutes))
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("roles", user.getRoles().name())
                    .sign(algorithm);

            return accessToken;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error creating JWT Token (Access Token):" + error.getMessage());
        }
    };

    public String generateRefreshToken(UsersEntity user, Integer expirationMinutes) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRETKEY");

            String refreshToken = JWT
                    .create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withClaim("type", "refresh")
                    .withExpiresAt(generateExpirationDate(expirationMinutes))
                    .sign(algorithm);

            return refreshToken;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error creating JWT Token (Refresh Token):" + error.getMessage());
        }
    }

    public String validateAccessToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRETKEY");

            return JWT.require(algorithm)
                    .withIssuer("contactsAPI")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Expired token. Please generate a new token.");
        }
    }

    /**
     * 1. Procura se o Refresh Token existe no banco de dados.
     * 2. Verifica se o Token já foi revogado.
     * 3. Verifica a expiração do token.
     * 4. Tira o Revoke de false pra true do oldRefreshToken
     * 5. (Futuro) validar a assinatura e claims (é do type = 'refresh'?)
     * 6. (Futuro) validar o ipAddress e userAgent (são os mesmos? não tem origens diferentes? será se alguem não roubou o refresh token?)
     */
    @Transactional
    public RefreshTokensEntity rotateRefreshToken(String oldRefreshToken) {

        RefreshTokensEntity stored = refreshTokensRepository
                .findByToken(oldRefreshToken)
                .orElseThrow(() ->
                        new RefreshTokenNotFoundException("Refresh token not found."));

        if (Boolean.TRUE.equals(stored.getRevoked())) {
            throw new RefreshTokenRevokedException("Refresh token already revoked.");
        }

        if (stored.getExpiresAt().isBefore(Instant.now())) {
            throw new RefreshTokenExpiredException("Refresh token expired.");
        }

        stored.setRevoked(true);

        return stored;
    }

    // # Possível ter múltiplas sessões por usuário. # //
    public RefreshTokensEntity addRefreshToken(
            String refreshToken, UUID userId, String ip, String userAgent
    ) {

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        RefreshTokensEntity refreshTokensEntity = new RefreshTokensEntity();
        refreshTokensEntity.setToken(refreshToken);
        refreshTokensEntity.setUser(user);
        refreshTokensEntity.setRevoked(false);
        refreshTokensEntity.setIpAddress(ip);
        refreshTokensEntity.setUserAgent(userAgent);
        refreshTokensEntity.setExpiresAt(generateExpirationDate(60 * 24 * 7));
        RefreshTokensEntity refreshTokenAdded = refreshTokensRepository.save(refreshTokensEntity);

        return refreshTokenAdded;
    }
}
