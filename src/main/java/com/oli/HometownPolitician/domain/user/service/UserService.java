package com.oli.HometownPolitician.domain.user.service;

import com.oli.HometownPolitician.domain.user.dto.TokenDto;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.global.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

import static com.oli.HometownPolitician.domain.user.equipment.UserPrefixEquipment.addPrefixUUID;
import static com.oli.HometownPolitician.domain.user.equipment.UserPrefixEquipment.deletePrefix;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;

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

    private String createUuid() {
        return UUID.randomUUID().toString();
    }
}
