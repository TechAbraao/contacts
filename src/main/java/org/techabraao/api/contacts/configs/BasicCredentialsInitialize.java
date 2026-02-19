package org.techabraao.api.contacts.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.enums.Roles;
import org.techabraao.api.contacts.repository.UsersRepository;

@Configuration
@RequiredArgsConstructor
public class BasicCredentialsInitialize {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.email}")
    private String basicEmail;
    @Value("${spring.security.user.name}")
    private String basicUsername;
    @Value("${spring.security.user.password}")
    private String basicPassword;


    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {
            if (!usersRepository.existsByUsername(basicUsername)) {

                UsersEntity admin = new UsersEntity();
                admin.setUsername(basicUsername);
                admin.setEmail(basicEmail);
                admin.setPassword(passwordEncoder.encode(basicPassword));
                admin.setRoles(Roles.ADMIN);
                usersRepository.save(admin);

            }
        };
    }
}