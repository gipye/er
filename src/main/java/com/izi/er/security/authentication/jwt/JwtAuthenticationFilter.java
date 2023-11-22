package com.izi.er.security.authentication.jwt;

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

        // jwt 토큰 리졸버를 통해 request 객체로부터 토큰 부분을 뽑는다.
        // 토큰이 두 개 이상 발견되면 예외처리 (바로 리턴할지 필터를 탈지 고민)
        // 토큰이 없으면 인증이 필요없는 접근일 수도 있으므로 다음 필터로 진행
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

        JwtTokenAuthenticationToken authentication = new JwtTokenAuthenticationToken(null, token);
        authentication.setDetails(this.authenticationDetailsSource.buildDetails(request));

        // AuthenticationManager를 통해 jwt 토큰으로 구성한 Authentication 객체에 대한 인증을 수행한다.
        // 인증이 성공하면 인증 정보를 담은 시큐리티 컨텍스트를 생성하여 컨텍스트 홀더에 저장한다.
        // 인증이 실패하면 예외가 던져지므로 catch 문으로 이동됨
        try {
            AuthenticationManager authenticationManager = this.authenticationManagerResolver.resolve(request);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);

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
