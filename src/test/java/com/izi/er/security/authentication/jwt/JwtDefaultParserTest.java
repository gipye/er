package com.izi.er.security.authentication.jwt;

import com.izi.er.security.authentication.jwt.parser.DefaultJwtDecoder;
import com.izi.er.security.authentication.jwt.parser.DefaultJwtEncoder;
import com.izi.er.security.authentication.jwt.parser.JwtDecoder;
import com.izi.er.security.authentication.jwt.parser.JwtEncoder;
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

        Jwt jwt = createTestJwt();

        String token = encoder.encode(jwt);

        Jwt decodedJwt = decoder.decode(token);

        Assertions.assertEquals(jwt, decodedJwt);
        Assertions.assertNotSame(jwt, decodedJwt);
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
