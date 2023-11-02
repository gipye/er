package com.izi.er.security.authentication.jwt;

public class JwtProcessingException extends RuntimeException {
    public JwtProcessingException() {
        super();
    }
    public JwtProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
    public JwtProcessingException(Throwable cause) {
        super(cause);
    }
}
