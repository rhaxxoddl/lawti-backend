package com.oli.HometownPolitician.domain.committee.dto;

import com.oli.HometownPolitician.domain.committee.entity.Committee;
import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommitteeDto {
    private Long committeeId;
    private String name;

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public CommitteeDto(Long committeeId, String name) {
        Assert.notNull(committeeId, "committeeId에 null이 들어올 수 없습니다");
        Assert.notNull(name, "name에 null이 들어올 수 없습니다");

        this.committeeId = committeeId;
        this.name = name;
    }
    static public CommitteeDto from(Committee committee) {
        if (committee == null)
            return null;
        return InitBuilder()
                .committeeId(committee.getId())
                .name(committee.getName())
                .build();
    }
}
