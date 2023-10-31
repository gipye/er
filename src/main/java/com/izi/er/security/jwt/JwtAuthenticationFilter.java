package com.izi.er.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Setter
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;
    private BearerTokenResolver bearerTokenResolver = new JwtTokenResolver();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManagerResolver = (request) -> authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        try {
            token = bearerTokenResolver.resolve(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (token == null) {
            this.logger.trace("Not found bearer token");
            filterChain.doFilter(request, response);
            return;
        }
    }

}
