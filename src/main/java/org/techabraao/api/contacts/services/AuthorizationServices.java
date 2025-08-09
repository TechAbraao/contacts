package org.techabraao.api.contacts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.techabraao.api.contacts.model.UsersModel;
import org.techabraao.api.contacts.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthorizationServices implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user;
    }
}
