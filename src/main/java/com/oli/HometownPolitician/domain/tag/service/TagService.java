package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.userTagRelation.repository.UserTagRelationRepository;
import com.oli.HometownPolitician.domain.tag.dto.TagInput;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class TagService {
    private final TagRepository tagRepository;
    private final UserTagRelationRepository userTagRelationRepository;
    private final UserRepository userRepository;
    private final String UUID_PREFIX = "UUID-";
    private final String BEARER_PREFIX = "Bearer";
    public TagsDto queryTags() {
        return TagsDto
                .from(tagRepository.findAll());
    }

    public TagsDto queryFollowedTagsByAuthorization(String authorization) {
        String userUuid =  deletePrefix(authorization);
        return TagsDto.from(
                userTagRelationRepository.qFindFollowedTagsByUuid(userUuid)
                        .stream()
                        .map(userTagRelation -> userTagRelation.getTag())
                        .collect(Collectors.toList())
        );
    }
    public void followingTags(TagsInput tagsInput, String authorization) {
        String userUuid = deletePrefix(authorization);
        User user = userRepository.qFindByUuidWithFollowedTags(userUuid).get();
        List<String> tagNameList = tagsInput.getList()
                .stream()
                .map(tagInput -> tagInput.getName())
                .collect(Collectors.toList());
        List<Tag> tags = tagRepository.queryTagsByNameList(tagNameList);
        user.followingTags(tags);
    }
    public void unfollowMyTags(TagsInput tagsInput, String authorization) {
        String userUuid = deletePrefix(authorization);
        User user = userRepository.qFindByUuidWithFollowedTags(userUuid).get();
        List<String> tagNameList = tagsInput.getList()
                .stream()
                .map(tagInput -> tagInput.getName())
                .collect(Collectors.toList());
        List<Tag> tags = tagRepository.queryTagsByNameList(tagNameList);
        user.unfollowingTags(tags);
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

    private User getUserByUuid(String uuid) {
        Optional<User> user = userRepository.qFindByUuid(uuid);
        if (user.isEmpty())
            throw new NotFoundError("해당하는 사용자를 찾을 수 없습니다.");
        return user.get();
    }

    private boolean isFollowed(TagInput tagInput, List<UserTagRelation> followedTags) {
        return followedTags
                .stream()
                .filter(followedTag -> followedTag.getTag().getId().equals(tagInput.getId()))
                .count() > 0;
    }
}
