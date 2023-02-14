package com.oli.HometownPolitician.domain.user.service;

import com.oli.HometownPolitician.domain.user.dto.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("익명 유저가 잘 생성되는지 확인")
    void createAnonymousUser_well_test() {
        String userUuid = userService.createAnonymousUser();
        assertThat(userUuid.getClass()).isEqualTo(String.class);
        assertThat(userUuid.length()).isEqualTo(36);
    }

    @Test
    @DisplayName("TokenDto가 잘 생성되는지 확인")
    void createTokenDto() {
        TokenDto tokenDto = userService.createTokenDto(userService.createAnonymousUser());
        assertThat(tokenDto).isNotNull();
        assertThat(tokenDto.getAccessToken()).isNotNull();
    }
}