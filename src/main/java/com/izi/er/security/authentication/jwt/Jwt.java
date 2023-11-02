package com.izi.er.security.authentication.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Builder
public class Jwt {
    private final Map<String, Object> headers;
    private final Map<String, Object> claims;
    public Jwt() {
        this.headers = new LinkedHashMap<>();
        this.claims = new LinkedHashMap<>();
    }
    public Jwt(Class<Map<String, Object>> mapClass) throws InstantiationException, IllegalAccessException {
        try {
            this.headers = mapClass.getDeclaredConstructor().newInstance();
            this.claims = mapClass.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
