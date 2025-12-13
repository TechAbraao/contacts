package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techabraao.api.contacts.dto.SignUpDTO;
import org.techabraao.api.contacts.dto.response.UsersResponse;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.mappers.UsersMapper;
import org.techabraao.api.contacts.repository.UsersRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Boolean verifyExistsUserByUsername(SignUpDTO user) {
        return repository.existsByEmail(user.email()) || repository.existsByUsername(user.username());
    };

    public Boolean addUser(SignUpDTO user) {
        String hashPassword = passwordEncoder.encode(user.password());
        var savedUser = repository.save(user.toUsersModel(hashPassword));
        return savedUser != null && savedUser.getId() != null;
    };

    @Transactional(readOnly = true)
    public UsersResponse searchUserById(UUID uuid) {
        UsersEntity user = repository.findById(uuid).orElse(null);
        return UsersMapper.toResponse(user);
    }

    public List<UsersResponse> allUsers() {
        List<UsersEntity> allUsers = repository.findAll();
        return allUsers.stream()
                .map(UsersMapper::toResponse)
                .toList();
    }
}
