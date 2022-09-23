package edu.miu.cs544.gateway.domain.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    CLIENT;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
