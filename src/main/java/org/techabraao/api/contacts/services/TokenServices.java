package org.techabraao.api.contacts.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.entity.RefreshTokensEntity;
import org.techabraao.api.contacts.entity.UsersEntity;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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

    public String generateAccessToken(UsersEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRETKEY");

            String accessToken = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(generateExpirationDate(5))
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("roles", user.getRoles().name())
                    .sign(algorithm);

            return accessToken;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error creating JWT Token (Access Token):" + error.getMessage());
        }
    };

    public String generateRefreshToken(UsersEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRETKEY");

            String refreshToken = JWT
                    .create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withClaim("type", "refresh")
                    .withExpiresAt(generateExpirationDate(60 * 24 * 7))
                    .sign(algorithm);

            return refreshToken;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error creating JWT Token (Refresh Token):" + error.getMessage());
        }
    }

    public String validateToken(String token) {
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

    public RefreshTokensEntity addRefreshToken(String refreshToken, UUID userId) {

        UsersEntity user = usersRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        RefreshTokensEntity refreshTokensEntity = new RefreshTokensEntity();
        refreshTokensEntity.setToken(refreshToken);
        refreshTokensEntity.setUser(user);
        refreshTokensEntity.setRevoked(false);
        refreshTokensEntity.setExpiresAt(generateExpirationDate(60 * 24 * 7));
        RefreshTokensEntity refreshTokenAdded = refreshTokensRepository.save(refreshTokensEntity);

        return refreshTokenAdded;
    }
}
