package com.izi.er.security.authentication.jwt;

public interface JwtDecoder {
    Jwt decode(String token) throws JwtProcessingException;
}
