package com.oli.HometownPolitician.domain.committee.controller;

import com.oli.HometownPolitician.domain.committee.dto.CommitteesDto;
import com.oli.HometownPolitician.domain.committee.service.CommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class CommitteeController {
    private CommitteeService committeeService;
    @QueryMapping(name = "queryCommittees")
    public CommitteesDto queryCommittees() {
        return committeeService.queryCommittees();
    }
}
