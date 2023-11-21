package com.izi.er.security.authentication;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenAuthenticationException extends AuthenticationException {
    public JwtTokenAuthenticationException(String msg) {
        super(msg);
    }
    public JwtTokenAuthenticationException() {
        super("Failed Authenticate");
    }
    public JwtTokenAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
    public JwtTokenAuthenticationException(Throwable cause) {
        super("Failed Authenticate", cause);
    }
}
