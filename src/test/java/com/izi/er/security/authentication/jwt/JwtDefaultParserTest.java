package com.izi.er.security.authentication.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JwtDefaultParserTest {
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private final String key = "testkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkeytestkey";
    @Test
    public void encodeAndDecodeTest() {
        JwtEncoder encoder = new DefaultJwtEncoder(algorithm, key);
        JwtDecoder decoder = new DefaultJwtDecoder(algorithm, key);

        Jwt jwt = new Jwt();
        jwt.setAlgorithm(SignatureAlgorithm.HS256.getValue());
        jwt.setIssuer("server");
        jwt.setSubject("test_token");
        jwt.setAudience("test_user");
        jwt.setExpiration(new Date(3032475986763L));
        jwt.setIssuedAt(new Date(3032475986863L));
        jwt.put("jfalksedfjlaj", "fjlaewfjlkae");

        String token = encoder.encode(jwt);

        Jwt decodedJwt = decoder.decode(token);

        Assertions.assertEquals(jwt, decodedJwt);
        Assertions.assertNotSame(jwt, decodedJwt);

        decodedJwt.setExpiration(new Date(1L));
        Assertions.assertNotEquals(jwt, decodedJwt);
    }
}
