package com.izi.er.security.authentication.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtTest {
    /**
     * Jwt 객체의 addClaims(), put(), put(), claimSize() 메소드를 테스트한다.
     */
    @Test
    public void addTest() {
        ///처음 생성 시 headers와 claims의 크기는 0이어야 한다.
        Jwt jwt = new Jwt();
        Assertions.assertEquals(0, jwt.claimSize());

        jwt.put("a", 111);
        jwt.put("wefjle", 222);
        Assertions.assertAll(
                // put() 메소드는 원소를 claims에만 추가한다.
                () -> Assertions.assertEquals(2, jwt.claimSize()),
                () -> Assertions.assertEquals(111, jwt.get("a")),
                () -> Assertions.assertEquals(222, jwt.get("wefjle"))
        );

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("wefjle", 81);
        map.put("fkjelwjflkwejf", "testtest");

        jwt.addClaims(map);
        Assertions.assertAll(
                // addClaims() 메소드로 한번에 여러 원소 추가 시
                // "wefjle" 키값은 기존 claims에 있으므로 원소는 1개만 더 추가되며,
                // "wefjlw"에 대한 value는 81로 변경된다.
                () -> Assertions.assertEquals(3, jwt.claimSize()),
                () -> Assertions.assertEquals(81, jwt.get("wefjle")),
                () -> Assertions.assertEquals("testtest", jwt.get("fkjelwjflkwejf"))
        );
    }

    /**
     * Jwt 객체의 getter, setter 메소드를 테스트한다.
     */
    @Test
    public void setterTest() {
        Jwt jwt = new Jwt();

        jwt.setAlgorithm("HMAC_SHA256");
        jwt.setIssuer("server");
        jwt.setSubject("test_token");
        jwt.setAudience("test_user");
        jwt.setExpiration(new Date(3032475986763L));
        jwt.setIssuedAt(new Date(3032475986863L));

        Assertions.assertAll(
                () -> Assertions.assertEquals("HMAC_SHA256", jwt.getAlgorithm()),
                () -> Assertions.assertEquals("server", jwt.getIssuer()),
                () -> Assertions.assertEquals("test_token", jwt.getSubject()),
                () -> Assertions.assertEquals("test_user", jwt.getAudience()),
                () -> Assertions.assertEquals(new Date(3032475986763L), jwt.getExpiration()),
                () -> Assertions.assertEquals(new Date(3032475986863L), jwt.getIssuedAt()),
                () -> Assertions.assertNull(jwt.getJti())
        );
    }

    /**
     * equals 메소드를 테스트한다.
     */
    @Test
    public void equalsTest() {
        Jwt jwt1 = new Jwt();
        jwt1.setIssuer("server");
        jwt1.setSubject("test_token");
        jwt1.setAudience("test_user");
        jwt1.setExpiration(new Date(3032475986763L));
        jwt1.setIssuedAt(new Date(3032475986863L));

        Jwt jwt2 = new Jwt();
        jwt2.setIssuer("server");
        jwt2.setSubject("test_token");
        jwt2.setAudience("test_user");
        jwt2.setExpiration(new Date(3032475986763L));
        jwt2.setIssuedAt(new Date(3032475986863L));

        Assertions.assertEquals(jwt1, jwt2);
        Assertions.assertFalse(jwt1 == jwt2);
    }
}
