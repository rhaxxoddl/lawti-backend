package com.pivot.hp.hometownpolitician.security;

import com.pivot.hp.hometownpolitician.entity.User;
import com.pivot.hp.hometownpolitician.repository.UserRepository;
import graphql.com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        final User user = userRepository.findByIdentifier(identifier);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + identifier + "' not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(identifier)
                .password(user.getPassword())
                .authorities(Lists.newArrayList(user.getRole()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
