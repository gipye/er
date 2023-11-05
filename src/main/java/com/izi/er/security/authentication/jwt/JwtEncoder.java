package com.izi.er.security.authentication.jwt;

public interface JwtEncoder {
    String encode(Jwt jwt) throws JwtProcessingException;
}
