package com.izi.er.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class UserTest {
    @Test
    public void cloneTest() {
        User user = User.builder()
                .role(User.RoleType.ADMIN)
                .username("user-id")
                .password("pw haha")
                .id(1001)
                .createDate(new Timestamp(100001))
                .build();

        // 객체 복사
        User cloneUser = user.clone();

        // 복사된 객체의 주소는 달라야 하고, 담고 있는 정보는 같아야 한다.
        assertFalse(cloneUser == user);
        assertTrue(cloneUser.equals(user));
    }
}
