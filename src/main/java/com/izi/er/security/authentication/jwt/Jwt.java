package com.izi.er.security.authentication.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
public class Jwt {
    /** JWT {@code ALGORITHM} headers parameter name: <code>"alg"</code> */
    public static final String ALGORITHM = "alg";

    /** JWT {@code Issuer} claims parameter name: <code>"iss"</code> */
    public static final String ISSUER = "iss";

    /** JWT {@code Subject} claims parameter name: <code>"sub"</code> */
    public static final String SUBJECT = "sub";

    /** JWT {@code Audience} claims parameter name: <code>"aud"</code> */
    public static final String AUDIENCE = "aud";

    /** JWT {@code Expiration} claims parameter name: <code>"exp"</code> */
    public static final String EXPIRATION = "exp";

    /** JWT {@code Not Before} claims parameter name: <code>"nbf"</code> */
    public static final String NOT_BEFORE = "nbf";

    /** JWT {@code Issued At} claims parameter name: <code>"iat"</code> */
    public static final String ISSUED_AT = "iat";

    /** JWT {@code JWT ID} claims parameter name: <code>"jti"</code> */
    public static final String ID = "jti";

    private final Map<String, Object> headers;
    private final Map<String, Object> claims;
    public Jwt() {
        this.headers = new LinkedHashMap<>();
        this.claims = new LinkedHashMap<>();
    }

    /**
     * Headers와 Claims를 저장할 Map 자료구조 클래스를 지정하여 해당 자료구조 인스턴스를 생성하여 할당한다.
     * @param mapClass
     * @throws InstantiationException 클래스 객체가, abstract 클래스, 인터페이스, 배열 클래스, 원시형, 또는 void 를 나타내는 경우나
     * 클래스가 디폴트 생성자가 없는 경우
     * @throws IllegalAccessException 배열이 아닌 인스턴스를 반사적으로 만들거나, 필드를 설정 또는 가져오기하거나,
     * 메서드를 호출하려고 하지만 현재 실행 중인 메서드가 지정된 클래스, 필드, 메서드 또는 생성자의 정의에 액세스할 수 없는 경우
     */
    public Jwt(Class<? extends Map<String, Object>> mapClass) throws InstantiationException, IllegalAccessException {
        try {
            this.headers = mapClass.getDeclaredConstructor().newInstance();
            this.claims = mapClass.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /** alg getter, setter 메소드 **/
    public void setAlgorithm(Object alg) {
        this.headers.put(ALGORITHM, alg);
    }
    public Object getAlgorithm() {
        return this.headers.get(ALGORITHM);
    }

    /** issuer getter, setter 메소드 **/
    public void setIssuer(Object iss) {
        this.claims.put(ISSUER, iss);
    }
    public Object getIssuer() {
        return this.claims.get(ISSUER);
    }

    /** subject getter, setter 메소드 **/
    public void setSubject(Object sub) {
        this.claims.put(SUBJECT, sub);
    }
    public Object getSubject() {
        return this.claims.get(SUBJECT);
    }

    /** audience getter, setter 메소드 **/
    public void setAudience(Object aud) {
        this.claims.put(AUDIENCE, aud);
    }
    public Object getAudience() {
        return this.claims.get(AUDIENCE);
    }

    /** expiration date getter, setter 메소드 **/
    public void setExpiration(Object exp) {
        this.claims.put(EXPIRATION, exp);
    }
    public Object getExpiration() {
        return this.claims.get(EXPIRATION);
    }

    /** issued at getter, setter 메소드 **/
    public void setIssuedAt(Object iat) {
        this.claims.put(ISSUED_AT, iat);
    }
    public Object getIssuedAt() {
        return this.claims.get(ISSUED_AT);
    }

    /** jti getter, setter 메소드 **/
    public void setJti(Object jti) {
        this.claims.put(ID, jti);
    }
    public Object getJti() {
        return this.claims.get(ID);
    }

    /** 여러 원소 삽입 메소드 **/
    public void addClaims(Map<String, Object> claims) {
        this.claims.putAll(claims);
    }

    /** getter, setter about one claim **/
    public void put(String key, Object value) {
        this.claims.put(key, value);
    }
    public Object get(String key) {
        return this.claims.get(key);
    }

    public int claimSize() {
        return this.claims.size();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Jwt) {
            Jwt jwt = (Jwt)o;
            return jwt.headers.equals(this.headers) && jwt.claims.equals(this.claims);
        }
        return false;
    }
}
