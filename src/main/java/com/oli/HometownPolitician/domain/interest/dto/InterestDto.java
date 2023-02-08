package com.oli.HometownPolitician.domain.interest.dto;


import com.oli.HometownPolitician.domain.interest.entity.Interest;
import com.oli.HometownPolitician.domain.startMessage.dto.StartMessageDto;
import com.oli.HometownPolitician.domain.startMessage.entity.StartMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterestDto {
    private Long id;
    private String name;
    private boolean isFollowed;

    static public InterestDto from(Interest interest) {
        return InterestDto
                .builder()
                .id(interest.getId())
                .name(interest.getName())
                .build();
    }
}
