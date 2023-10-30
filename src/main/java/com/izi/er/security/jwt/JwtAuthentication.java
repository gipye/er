package com.izi.er.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private final UserDetails principal;
    private String credential;
    private boolean isAuthenticated;
    public JwtAuthentication(UserDetails principal, String credential) {
        this.principal = principal;
        this.credential = credential;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
