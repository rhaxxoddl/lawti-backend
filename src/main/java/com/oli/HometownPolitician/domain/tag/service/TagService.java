package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.dto.TagsInput;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class TagService {
    private final TagRepository tagRepository;
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
    public void followMyTags(TagsInput tagsInput) {
    }
    public void unfollowMyTags(TagsInput tagsInput) {
    }

    private String deleteUuidPrefix(String uuid) {
        if (uuid.contains(UUID_PREFIX))
            return uuid.substring(UUID_PREFIX.length());
        return uuid;
    }
}
