package org.techabraao.api.contacts.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.techabraao.api.contacts.repository.UsersRepository;

@RequiredArgsConstructor
@Component
public class UsersValidators {
    private final UsersRepository usersRepository;
}

