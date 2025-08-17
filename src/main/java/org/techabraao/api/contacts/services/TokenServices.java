package org.techabraao.api.contacts.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.entity.UsersModel;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenServices {
    public String generateToken(UsersModel user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRETKEY");

            String token = JWT.create()
                    .withIssuer("contactsAPI")
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(generateExpirationDate())
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error creating JWT Token:" + error.getMessage());
        }
    };

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

   private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
   }
}
