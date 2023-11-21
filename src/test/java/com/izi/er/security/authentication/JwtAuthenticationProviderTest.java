package com.izi.er.security.authentication;

import com.izi.er.security.authentication.jwt.DefaultJwtDecoder;
import com.izi.er.security.authentication.jwt.DefaultJwtEncoder;
import com.izi.er.security.authentication.jwt.Jwt;
import com.izi.er.security.authentication.jwt.JwtEncoder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Date;

public class JwtAuthenticationProviderTest {
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private static final String key = "testkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytes" +
            "tkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkey";
    @Test
    public void authenticateTest() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(new DefaultJwtDecoder(algorithm, key));
        JwtEncoder jwtEncoder = new DefaultJwtEncoder(algorithm, key);

        Jwt jwt = createTestJwt();
        String token = jwtEncoder.encode(jwt);
        Authentication authentication = new JwtTokenAuthenticationToken(null, token);

        Authentication authenticatedAuthentication = provider.authenticate(authentication);

        Assertions.assertTrue(authenticatedAuthentication.isAuthenticated());
    }

    @Test
    public void expirationTest() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(new DefaultJwtDecoder(algorithm, key));
        JwtEncoder jwtEncoder = new DefaultJwtEncoder(algorithm, key);

        Jwt jwt = createTestJwt();
        jwt.setExpiration(new Date(10L));
        String token = jwtEncoder.encode(jwt);
        Authentication authentication = new JwtTokenAuthenticationToken(null, token);

        Assertions.assertThrows(AuthenticationException.class, () -> provider.authenticate(authentication));
    }

    private Jwt createTestJwt() {
        Jwt jwt = new Jwt();
        Date now = new Date();

        jwt.setAlgorithm(SignatureAlgorithm.HS256.getValue());
        jwt.setIssuer("server");
        jwt.setSubject("test_token");
        jwt.setAudience("test_user");
        jwt.setExpiration(now);
        jwt.setIssuedAt(now);
        jwt.put("jfalksedfjlaj", "fjlaewfjlkae");
        return jwt;
    }
}
