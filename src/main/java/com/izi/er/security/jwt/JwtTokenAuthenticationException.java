package com.izi.er.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenAuthenticationException extends AuthenticationException {
    public JwtTokenAuthenticationException(String msg) {
        super(msg);
    }

}
