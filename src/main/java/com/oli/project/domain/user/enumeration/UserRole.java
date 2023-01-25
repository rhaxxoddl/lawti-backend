package com.oli.project.domain.user.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ROLE_USER, ROLE_CREATOR, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }

}
