package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techabraao.api.contacts.dto.SignUpDTO;
import org.techabraao.api.contacts.dto.request.SignUpRequest;
import org.techabraao.api.contacts.dto.request.UpdateUserRequest;
import org.techabraao.api.contacts.dto.response.UsersResponse;
import org.techabraao.api.contacts.entity.UsersEntity;
import org.techabraao.api.contacts.exceptions.UserEmailAlreadyExistsException;
import org.techabraao.api.contacts.exceptions.UserNotFoundException;
import org.techabraao.api.contacts.exceptions.UserUsernameAlreadyExistsException;
import org.techabraao.api.contacts.mappers.UsersMapper;
import org.techabraao.api.contacts.repository.UsersRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Boolean verifyExistsUserByUsername(SignUpRequest user) {
        return repository.existsByEmail(user.email()) || repository.existsByUsername(user.username());
    };

    public UsersResponse addUser(SignUpRequest userInfos) {
        // Cenário 1: Transformar o password em Hashcode. //
        String hashPassword = passwordEncoder.encode(userInfos.password());

        // Cenário 2: Transformar o User Request para User Entity (entidade do banco de dados). //
        UsersEntity userRequestToEntity = UsersMapper.toEntity(userInfos, hashPassword);

        // Cenário 3: Verificar se o campo 'email' já existe no banco de dados. //
        Boolean existsEmail = repository.existsByEmail(userRequestToEntity.getEmail());
        if (existsEmail) { throw new UserEmailAlreadyExistsException("E-mail already in use."); }

        // Cenário 4: Verificar se o campo 'username' já existe no banco de dados. //
        Boolean existsUsername = repository.existsByUsername(userRequestToEntity.getUsername());
        if (existsUsername) { throw new UserUsernameAlreadyExistsException("Username already in use."); }

        // Cenário 5: Salvar a entidade no banco de dados. //
        var savedUser = repository.save(userRequestToEntity);

        // Cenário 6: Retornando a entidade persistida no banco de dados. //
        return UsersMapper.toResponse(savedUser);
    };

    @Transactional(readOnly = true)
    public UsersResponse searchUserById(UUID uuid) {
        UsersEntity user = repository.findById(uuid).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        return UsersMapper.toResponse(user);
    };

    public List<UsersResponse> allUsers() {
        List<UsersEntity> allUsers = repository.findAll();
        return allUsers.stream()
                .map(UsersMapper::toResponse)
                .toList();
    };

    public Void deleteUserById(UUID uuid) {
        Boolean existsUserById = repository.existsById(uuid);
        if (existsUserById) {
            repository.deleteById(uuid);
        } else {
            throw new UserNotFoundException("User not found.");
        }
        return null;
    };

    @Transactional
    public UsersResponse userChanged(UUID uuid, UpdateUserRequest request) {

        var user = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if (request.username() != null) {
            user.setUsername(request.username());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        return UsersMapper.toResponse(user);
    }


}
