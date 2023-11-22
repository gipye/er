package com.izi.er.security.authentication.jwt.parser;

import com.izi.er.security.authentication.jwt.Jwt;

public interface JwtDecoder {
    Jwt decode(String token) throws JwtProcessingException;
}
