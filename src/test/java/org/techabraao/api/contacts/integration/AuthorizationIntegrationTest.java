package org.techabraao.api.contacts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.techabraao.api.contacts.dto.SignUpDTO;
import org.techabraao.api.contacts.enums.Roles;
import org.techabraao.api.contacts.repository.UsersRepository;
import org.techabraao.api.contacts.services.AuthorizationServices;

import javax.xml.transform.Result;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Integration")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorizationServices authorizationServices;

    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    public void cleanDatabase() {
        usersRepository.deleteAll();
    }

    private ResultActions performPostSignUp(SignUpDTO payloadCredentials) throws Exception {
        return mockMvc.perform(
                post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payloadCredentials))
        );
    }

    @Test
    void testSignUpEndpointSuccess() throws Exception {

        SignUpDTO payloadCredentials = new SignUpDTO(
                "BruceWayne",
                "bruce.wayne@hotmail.com",
                Roles.USER,
                "BruceWaynePass"
        );

        ResultActions response = performPostSignUp(payloadCredentials)
                .andExpect(status().isCreated());

        response
                .andExpect(
                        jsonPath("$.message").value("User created successfully."))
                .andExpect(
                        jsonPath("$.statusCode").value(HttpStatus.CREATED.value())
                );
    }

    @Test
    void testSignUpEndpointWithInvalidPassword() throws Exception {

        SignUpDTO payloadCredentials = new SignUpDTO(
                "BruceWayne",
                "bruce.wayne@hotmail.com",
                Roles.USER,
                "BruceWaynePassword"
        );

        ResultActions response = performPostSignUp(payloadCredentials)
                .andExpect(status().isBadRequest());

        response
                .andExpect(jsonPath("$.message").value("Validation failed."))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testSignUpEndpointWithDuplicateCredentials() throws Exception {
        SignUpDTO payloadCredentials = new SignUpDTO(
                "BruceWayne",
                "bruce.wayne@hotmail.com",
                Roles.USER,
                "BruceWaynePass"
        );

        ResultActions responseCreated = performPostSignUp(payloadCredentials)
                .andExpect(status().isCreated());

        ResultActions responseConflit = performPostSignUp(payloadCredentials)
                .andExpect(status().isConflict());

        responseConflit
                .andExpect(jsonPath("$.message").value("Email or username already in use."))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CONFLICT.value()));

    }
}
