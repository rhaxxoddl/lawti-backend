package com.oli.HometownPolitician.domain.user.service;

import com.oli.HometownPolitician.domain.user.dto.TokenDto;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;

    public String createAnonymousUser() {
        User newAnonymousUser = new User(UUID.randomUUID().toString());
        userRepository.save(newAnonymousUser);
        return newAnonymousUser.getUuid();
    }

    public TokenDto createTokenDto(String userUuid) {
        return new TokenDto(userUuid);
    }
}
