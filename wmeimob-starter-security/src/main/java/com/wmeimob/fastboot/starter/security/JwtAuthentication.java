package com.wmeimob.fastboot.starter.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtAuthentication extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 1L;
    private UserDetails principal;

    public JwtAuthentication(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }
}

