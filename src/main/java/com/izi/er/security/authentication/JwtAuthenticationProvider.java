package com.izi.er.security.authentication;

import com.izi.er.security.authentication.jwt.DefaultJwtDecoder;
import com.izi.er.security.authentication.jwt.JwtDecoder;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

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

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
