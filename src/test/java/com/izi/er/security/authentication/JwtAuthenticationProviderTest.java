package com.izi.er.security.authentication;

import com.izi.er.security.authentication.jwt.DefaultJwtDecoder;
import com.izi.er.security.authentication.jwt.DefaultJwtEncoder;
import com.izi.er.security.authentication.jwt.Jwt;
import com.izi.er.security.authentication.jwt.JwtEncoder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtAuthenticationProviderTest {
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private static final String key = "testkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkey";
    @Test
    public void authencateTest() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(new DefaultJwtDecoder(algorithm, key));
        JwtEncoder jwtEncoder = new DefaultJwtEncoder(algorithm, key);

        String token = null;
        Jwt jwt = new Jwt();
        jwt.setAlgorithm(SignatureAlgorithm.HS256.getValue());
        jwt.setIssuer("server");
        jwt.setSubject("test_token");
        jwt.setAudience("test_user");
        jwt.setExpiration(new Date(3032475986763L));
        jwt.setIssuedAt(new Date(3032475986863L));
        jwt.put("jfalksedfjlaj", "fjlaewfjlkae");

        token = jwtEncoder.encode(jwt);
        Authentication authentication = new JwtTokenAuthenticationToken(token);
    }
}
