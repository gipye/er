package com.izi.er.security.jwt;

import javax.servlet.http.HttpServletRequest;

public interface BearerTokenResolver {
    String resolve(HttpServletRequest request);
}
