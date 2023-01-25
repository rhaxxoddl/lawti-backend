package com.oli.project.domain.security.provider;

import graphql.com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String identifierWithRoles) throws UsernameNotFoundException {
        int index = identifierWithRoles.indexOf(":");
        String identifier = identifierWithRoles.substring(0, index);
        String roles = identifierWithRoles.substring(index + 1);
        return org.springframework.security.core.userdetails.User.withUsername(identifier).password(identifier).authorities((GrantedAuthority) Lists.newArrayList(roles)).accountLocked(false).accountExpired(false).credentialsExpired(false).disabled(false).build();
    }

}
