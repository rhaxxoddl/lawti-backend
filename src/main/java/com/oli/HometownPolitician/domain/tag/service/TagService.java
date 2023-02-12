package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.dto.TagsInput;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.global.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final String UUID_PREFIX = "UUID-";
    public TagsDto queryTags() {
        return TagsDto
                .from(tagRepository.findAll());
    }

    public TagsDto queryFollowedTagsByAuthorization(String authorization) {
        String userUuid =  deleteUuidPrefix(authorization);
        return TagsDto.from(
                tagRepository.qFindFollowedTagsByUserUuid(userUuid)
        );
    }
    public void followMyTags(TagsInput tagsInput, String authorization) {
        String userUuid = deleteUuidPrefix(authorization);
        User user = getUserByUuid(userUuid);
//        user
//        if ()
    }
    public void unfollowMyTags(TagsInput tagsInput) {
    }

    private String deleteUuidPrefix(String uuid) {
        if (uuid.contains(UUID_PREFIX))
            return uuid.substring(UUID_PREFIX.length());
        return uuid;
    }

    private User getUserByUuid(String uuid) {
        Optional<User> user = userRepository.qFindByUuid(uuid);
        if (user.isEmpty())
            throw new NotFoundError("해당하는 사용자를 찾을 수 없습니다.");
        return user.get();
    }
}
