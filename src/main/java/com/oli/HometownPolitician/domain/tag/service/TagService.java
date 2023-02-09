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
    public TagsDto queryTags() {
        return TagsDto
                .from(tagRepository.findAll());
    }
    public void queryFollowedMyTags(TagsInput tagsInput) {
    }
    public void followMyTags(TagsInput tagsInput) {
    }
    public void unfollowMyTags(TagsInput tagsInput) {
    }

}
