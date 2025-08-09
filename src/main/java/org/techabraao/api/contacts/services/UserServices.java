package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.dto.request.SignUpDTO;
import org.techabraao.api.contacts.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Boolean verifyExistsUserByUsername(SignUpDTO user) {
        return repository.existsByEmail(user.email()) || repository.existsByUsername(user.username());
    };

    public Boolean addUser(SignUpDTO user) {
        String hashPassword = passwordEncoder.encode(user.password());
        var savedUser = repository.save(user.toUsersModel(hashPassword));
        return savedUser != null && savedUser.getId() != null;
    };
}
