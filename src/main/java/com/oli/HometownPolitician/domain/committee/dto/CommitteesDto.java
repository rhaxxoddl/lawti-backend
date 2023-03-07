package com.oli.HometownPolitician.domain.committee.dto;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CommitteesDto {
    private List<CommitteeDto> list;

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public CommitteesDto(List<CommitteeDto> list) {
        Assert.notNull(list, "list에 null이 들어올 수 없습니다");

        this.list = list;
    }

    static public CommitteesDto from(List<Committee> committees) {
        if (committees == null)
            return null;
        return CommitteesDto.InitBuilder()
                .list(
                        committees.stream()
                                .map(CommitteeDto::from)
                                .toList()
                )
                .build();
    }
}
