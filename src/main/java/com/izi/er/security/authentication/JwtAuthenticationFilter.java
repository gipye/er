package com.izi.er.security.authentication;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
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
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private JwtTokenResolver jwtTokenResolver = new JwtTokenResolver();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManagerResolver = (request) -> authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        try {
            token = jwtTokenResolver.resolve(request);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        if (token == null) {
            this.logger.trace("Not found bearer token");
            filterChain.doFilter(request, response);
            return;
        }

        JwtTokenAuthenticationToken authentication = new JwtTokenAuthenticationToken(token);
        authentication.setDetails(this.authenticationDetailsSource.buildDetails(request));

        try {
            // AuthenticationManager를 통해 jwt 토큰으로 구성한 Authentication 객체에 대한 인증을 수행한다.
            AuthenticationManager authenticationManager = this.authenticationManagerResolver.resolve(request);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);

            // 인증이 성공하면 인증 정보를 담은 시큐리티 컨텍스트를 생성하여 컨텍스트 홀더에 저장한다.
            // 인증이 실패하면 예외가 던져지므로 catch 문으로 이동됨
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authenticationResult);
            this.securityContextHolderStrategy.setContext(context);
            this.securityContextRepository.saveContext(context, request, response);

            filterChain.doFilter(request, response);
        } catch(AuthenticationException e) {
            this.securityContextHolderStrategy.clearContext();
            throw e;
        }
    }

}
