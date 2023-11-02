package com.izi.er.security.authentication;

import com.izi.er.security.authentication.jwt.JwtDecoder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;
    public JwtAuthenticationProvider(JwtDecoder jwtDecoder) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuthenticationToken authenticationToken = (JwtTokenAuthenticationToken) authentication;

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}