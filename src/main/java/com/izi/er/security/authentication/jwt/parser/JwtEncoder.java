package com.izi.er.security.authentication.jwt.parser;

import com.izi.er.security.authentication.jwt.Jwt;

public interface JwtEncoder {
    String encode(Jwt jwt) throws JwtProcessingException;
}
