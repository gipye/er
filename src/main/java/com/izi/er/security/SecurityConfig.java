package com.izi.er.security;

import com.izi.er.security.jwt.JwtAuthenticationFilter;
import com.izi.er.security.jwt.JwtAuthenticationProvider;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/admin/test").hasRole("ADMIN")
                        .antMatchers("/ordinary/test").hasAnyRole("ADMIN", "ORDINARY")
                        .antMatchers("/**").permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationProvider providerManager() {
        return new JwtAuthenticationProvider(new NimbusJwtDecoder(new DefaultJWTProcessor<SecurityContext>()));
    }
}
