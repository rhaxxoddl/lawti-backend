package com.oli.HometownPolitician.domain.tag.service;

import com.oli.HometownPolitician.domain.tag.dto.TagInput;
import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.dto.TagsInput;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.tag.repository.TagRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.equipment.UserPrefixEquipment;
import com.oli.HometownPolitician.domain.user.service.UserService;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.userTagRelation.repository.UserTagRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class TagService {
    private final TagRepository tagRepository;
    private final UserTagRelationRepository userTagRelationRepository;
    private final UserService userService;
    public TagsDto queryTags() {
        return TagsDto
                .from(tagRepository.findAll());
    }

    public TagsDto queryFollowedTagsByAuthorization(String authorization) {
        return TagsDto.from(
                userTagRelationRepository.qFindFollowedTagsByUuid(UserPrefixEquipment.deletePrefix(authorization))
                        .stream()
                        .map(UserTagRelation::getTag)
                        .collect(Collectors.toList())
        );
    }
    public TagsDto followingTags(TagsInput tagsInput, String authorization) {
        User user = userService.getUserWithFollowedTags(authorization);
        List<String> tagNameList = tagsInput.getList()
                .stream()
                .map(TagInput::getName)
                .collect(Collectors.toList());
        List<Tag> tags = tagRepository.queryTagsByNameList(tagNameList);
        user.followTags(tags);
        return TagsDto.from(user.getFollowingTags());
    }
    public TagsDto unfollowMyTags(TagsInput tagsInput, String authorization) {
        User user = userService.getUserWithFollowedTags(authorization);
        List<String> tagNameList = tagsInput.getList()
                .stream()
                .map(TagInput::getName)
                .collect(Collectors.toList());
        List<Tag> tags = tagRepository.queryTagsByNameList(tagNameList);
        user.unfollowTags(tags);
        return TagsDto.from(user.getFollowingTags());
    }
}
