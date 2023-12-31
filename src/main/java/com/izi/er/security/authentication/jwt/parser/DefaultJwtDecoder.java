package com.izi.er.security.authentication.jwt.parser;

import com.izi.er.security.authentication.jwt.Jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class DefaultJwtDecoder implements JwtDecoder {
    private final SignatureAlgorithm algorithm;
    private final String key;
    public DefaultJwtDecoder(SignatureAlgorithm algorithm, String key) {
        this.algorithm = algorithm;
        this.key = key;
    }
    @Override
    public Jwt decode(String token) throws JwtProcessingException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtProcessingException(e);
        }

        Jwt jwt = new Jwt();
        jwt.addClaims(claims);
        jwt.setAlgorithm("HS256");

        long exp = (Long) jwt.getExpiration();
        long iat = (Long) jwt.getIssuedAt();
        jwt.setExpiration(new Date(exp));
        jwt.setIssuedAt(new Date(iat));

        return jwt;
    }
}
