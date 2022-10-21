package com.pivot.hp.hometownpolitician.repository;

import com.pivot.hp.hometownpolitician.annotation.EnableQueryLog;
import com.pivot.hp.hometownpolitician.entity.User;
import com.pivot.hp.hometownpolitician.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@EnableQueryLog
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void save() {
        User user = new User();
        user.setEmail("bigpel66@icloud.com");
        user.setNickname("BIGPEL");
        user.setPassword("jseo");
        user.setRole(UserRole.ROLE_ADMIN);
        User savedUser = userRepository.save(user);
        assertThat(user.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    public void findByIdentifier() {
        User user = new User();
        user.setEmail("bigpel66@icloud.com");
        user.setNickname("BIGPEL");
        user.setPassword("jseo");
        user.setRole(UserRole.ROLE_ADMIN);
        User savedUser = userRepository.save(user);
        assertThat(user.getId()).isEqualTo(savedUser.getId());

        User byIdentifier = userRepository.findByIdentifier("bigpel66@icloud.com");
        assertThat(savedUser.getEmail()).isEqualTo(byIdentifier.getEmail());
        assertThat(savedUser.getNickname()).isEqualTo(byIdentifier.getNickname());
        assertThat(savedUser.getPassword()).isEqualTo(byIdentifier.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(byIdentifier.getRole());
        assertThat(savedUser).isEqualTo(byIdentifier);
    }

}
