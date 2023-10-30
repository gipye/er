package com.izi.er.user;

import com.izi.er.user.hospital.HospitalUser;
import com.izi.er.user.hospital.HospitalUserRepository;
import com.izi.er.user.ordinary.OrdinaryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    HospitalUserRepository hospitalUserRepository;
    @Autowired
    OrdinaryUserRepository ordinaryUserRepository;

    @AfterEach
    public void end() {
        hospitalUserRepository.deleteAll();
        ordinaryUserRepository.deleteAll();
    }

    @Test
    public void hospitalUserTest() {
    }

    @Test
    public void ordinaryUserTest() {
    }
}
