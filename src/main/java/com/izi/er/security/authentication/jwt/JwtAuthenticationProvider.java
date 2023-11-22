package com.izi.er.security.authentication.jwt;

import com.izi.er.security.authentication.jwt.parser.DefaultJwtDecoder;
import com.izi.er.security.authentication.jwt.parser.JwtDecoder;
import com.izi.er.security.authentication.jwt.parser.JwtProcessingException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import java.util.Date;

@Setter
@NoArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private JwtDecoder jwtDecoder = new DefaultJwtDecoder(SignatureAlgorithm.HS256, "a");

    public JwtAuthenticationProvider(JwtDecoder jwtDecoder) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuthenticationToken authenticationToken = (JwtTokenAuthenticationToken) authentication;
        Jwt jwt = getJwt(authenticationToken);

        Date expiration = (Date) jwt.getExpiration();
        if (expiration.before(new Date())) {
            authenticationToken.setAuthenticated(true);
            return authenticationToken;
        }
        throw new JwtTokenAuthenticationException();
    }

    private Jwt getJwt(JwtTokenAuthenticationToken token) {
        try {
            return this.jwtDecoder.decode(token.getToken());
        } catch (JwtProcessingException failed) {
            throw new JwtTokenAuthenticationException(failed.getMessage(), failed);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
