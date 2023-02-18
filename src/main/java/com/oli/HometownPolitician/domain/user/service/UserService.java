package com.oli.HometownPolitician.domain.user.service;

import com.oli.HometownPolitician.domain.user.dto.TokenDto;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.global.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final String UUID_PREFIX = "UUID-";
    private final String BEARER_PREFIX = "Bearer ";

    public String createAnonymousUser() {
        User newAnonymousUser = new User(createUuid());
        userRepository.save(newAnonymousUser);
        return newAnonymousUser.getUuid();
    }
    public User getUser(String authorization) {
        String userUuid = deletePrefix(authorization);
        return userRepository.qFindByUuid(userUuid).orElseThrow(() -> new NotFoundError("해당하는 유저가 존재하지 않습니다"));
    }
    public User getUserWithFollowedTags(String authorization) {
        String userUuid = deletePrefix(authorization);
        return userRepository.qFindByUuidWithFollowedTags(userUuid).orElseThrow(() -> new NotFoundError("해당하는 유저가 존재하지 않습니다"));
    }

    public TokenDto createTokenDto(String userUuid) {
        return new TokenDto(addPrefixUUID(userUuid));
    }


    private String addPrefixUUID(String uuid) {
        return UUID_PREFIX + uuid;
    }

    private String createUuid() {
        return UUID.randomUUID().toString();
    }

    private String deletePrefix(String authorization) {
        return deleteUuidPrefix(deleteBearerPrefix(authorization));
    }

    private String deleteUuidPrefix(String uuid) {
        if (uuid.contains(UUID_PREFIX))
            return uuid.substring(UUID_PREFIX.length());
        return uuid;
    }

    private String deleteBearerPrefix(String bearerToken) {
        if (bearerToken.contains(BEARER_PREFIX))
            return bearerToken.substring(BEARER_PREFIX.length());
        return bearerToken;
    }
}
