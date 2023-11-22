package com.izi.er.security.authentication.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izi.er.security.authentication.jwt.JwtTokenAuthenticationToken;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");
    private static final String CONTENT_TYPE = "application/json";
    private String username;
    private String password;
    private boolean postOnly = true;
    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    {
        setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());
    }
    public LoginAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            Authentication authenticationResult = attemptAuthentication(request, response);
            if (authenticationResult == null) {
                return;
            }
            JwtTokenAuthenticationToken authResult = (JwtTokenAuthenticationToken) authenticationResult;
            successfulAuthentication(request, response, chain, authenticationResult);
            response.addHeader(this.bearerTokenHeaderName, "Bearer " + authResult.getToken());
        }
        catch (InternalAuthenticationServiceException failed) {
            this.logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);
        }
        catch (AuthenticationException ex) {
            // Authentication failed
            unsuccessfulAuthentication(request, response, ex);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        parseLoginInfo(request);

        String username = obtainUsername(request);
        username = (username != null) ? username.trim() : "";
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        LoginAuthenticationToken authRequest = new LoginAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainUsername(HttpServletRequest request) {
        return this.username;
    }

    private String obtainPassword(HttpServletRequest request) {
        return this.username;
    }

    private void parseLoginInfo(HttpServletRequest request) {
        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        @Data
        class LoginData {
            private String username;
            private String password;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginData loginData = objectMapper.readValue(jsonString.toString(), LoginData.class);
            this.username = loginData.getUsername();
            this.password = loginData.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
