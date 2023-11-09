package com.izi.er.user.ordinary;

import com.izi.er.user.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class OrdinaryUserTest {
    @Test
    public void cloneTest() {
        User user = User.builder()
                .role(User.RoleType.ADMIN)
                .username("user-id")
                .password("pw haha")
                .id(1001)
                .createDate(new Timestamp(100001))
                .build();
        OrdinaryUser ordinaryUser = OrdinaryUser.builder()
                .user(user)
                .name("홍길동")
                .address("우산도")
                .longitude(10.2)
                .longitude(11.3)
                .telephone("010123123")
                .build();

        // 객체 복사
        OrdinaryUser cloneUser = ordinaryUser.clone();

        // 복사된 객체는 서로 주소가 달라야 한다.
        assertNotSame(cloneUser, ordinaryUser);

        // 복사된 객체의 참조 또한 주소가 달라야 한다.
        assertNotSame(cloneUser.getUser(), ordinaryUser.getUser());

        // 주소는 달라도 정보는 같아야 한다.
        assertTrue(cloneUser.equals(ordinaryUser));
        assertTrue(cloneUser.getUser().equals(ordinaryUser.getUser()));
    }
}
