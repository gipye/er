package com.izi.er.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.Assert;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;
    public JwtAuthenticationProvider(NimbusJwtDecoder jwtDecoder) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
