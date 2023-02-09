package com.oli.HometownPolitician.domain.tag.controller;

import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.dto.TagsInput;
import com.oli.HometownPolitician.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class TagController {
    private final TagService tagService;

    @QueryMapping(name = "queryInterests")
    public TagsDto queryInterests() {
        return tagService.queryInterests();
    }

    @QueryMapping(name = "queryFollowedInterests")
    public TagsDto queryFollowedInterests() {
        return null;
    }
    @MutationMapping(name = "followMyInterests")
    public void followMyInterests(TagsInput tagsInput) {
    }
    @MutationMapping(name = "unfollowMyInterests")
    public void unfollowMyInterests(TagsInput tagsInput) {
    }
}
