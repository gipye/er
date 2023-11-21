package com.izi.er.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collections;

public class JwtTokenAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object credentials;

    private final String token;

    /**
     * jwt 토큰 인증 토큰 생성자
     *
     * @param token jwt 토큰 문자열
     */
    public JwtTokenAuthenticationToken(Object credentials, String token) {
        super(Collections.emptyList());
        Assert.hasText(token, "token cannot be empty");
        this.credentials = credentials;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.getToken();
    }

    @Override
    public Object getPrincipal() {
        return this.getToken();
    }
}
