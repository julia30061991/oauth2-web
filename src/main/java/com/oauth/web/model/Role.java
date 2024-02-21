package com.oauth.web.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority, Serializable {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String vale;

    Role(String vale) {
        this.vale = vale;
    }

    @Override
    public String getAuthority() {
        return vale;
    }
}