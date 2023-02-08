package com.oli.HometownPolitician.domain.interest.controller;

import com.oli.HometownPolitician.domain.interest.dto.InterestsDto;
import com.oli.HometownPolitician.domain.interest.dto.InterestsInput;
import com.oli.HometownPolitician.domain.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class InterestController {
    private final InterestService interestService;

    @QueryMapping(name = "queryInterests")
    public InterestsDto queryInterests() {
        return interestService.queryInterests();
    }

    @QueryMapping(name = "queryFollowedInterests")
    public InterestsDto queryFollowedInterests() {
        return null;
    }
    @MutationMapping(name = "followMyInterests")
    public void followMyInterests(InterestsInput interestsInput) {
    }
    @MutationMapping(name = "unfollowMyInterests")
    public void unfollowMyInterests(InterestsInput interestsInput) {
    }
}
