package com.oli.HometownPolitician.domain.tag.controller;

import com.oli.HometownPolitician.domain.tag.dto.TagsDto;
import com.oli.HometownPolitician.domain.tag.dto.TagsInput;
import com.oli.HometownPolitician.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class TagController {
    private final TagService tagService;

    @QueryMapping(name = "queryTags")
    public TagsDto queryTags() {
        return tagService.queryTags();
    }

    @QueryMapping(name = "queryFollowedTags")
    public TagsDto queryFollowedTags(@RequestHeader(name = "Authorization") String token)
    {
        return tagService.queryFollowedTagsByUserUuid(token);
    }
    @MutationMapping(name = "followMyTags")
    public void followMyTags(TagsInput tagsInput) {
    }
    @MutationMapping(name = "unfollowMyTags")
    public void unfollowMyTags(TagsInput tagsInput) {
    }
}
