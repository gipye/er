package com.izi.er.user.ordinary;

import com.izi.er.user.User;
import com.izi.er.user.hospital.HospitalUser;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HospitalUserTest {
    @Test
    public void cloneTest() {
        User user = User.builder()
                .role(User.RoleType.ADMIN)
                .username("user-id")
                .password("pw haha")
                .id(1001)
                .createDate(new Timestamp(100001))
                .build();
        HospitalUser hospitalUser = HospitalUser.builder()
                .user(user)
                .name("홍길동")
                .address("우산도")
                .longitude(10.2)
                .longitude(11.3)
                .telephone("010123123")
                .numberOfBed(5)
                .build();

        // 객체 복사
        HospitalUser cloneUser = hospitalUser.clone();

        // 복사된 객체는 서로 주소가 달라야 한다.
        assertFalse(cloneUser == hospitalUser);

        // 복사된 객체의 참조 또한 주소가 달라야 한다.
        assertFalse(cloneUser.getUser() == hospitalUser.getUser());

        // 주소는 달라도 정보는 같아야 한다.
        assertTrue(cloneUser.equals(hospitalUser));
        assertTrue(cloneUser.getUser().equals(hospitalUser.getUser()));
    }
}
