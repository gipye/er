package com.izi.er.security.authentication.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class DefaultJwtEncoder implements JwtEncoder {
    private final SignatureAlgorithm algorithm;
    private final String key;
    public DefaultJwtEncoder(SignatureAlgorithm algorithm, String key) {
        this.algorithm = algorithm;
        this.key = key;
    }
    @Override
    public String encode(Jwt jwt) throws JwtProcessingException {
        return Jwts.builder()
                .setHeader(jwt.getHeaders())
                .setClaims(jwt.getClaims())
                .signWith(algorithm, key)
                .compact();
    }
}
