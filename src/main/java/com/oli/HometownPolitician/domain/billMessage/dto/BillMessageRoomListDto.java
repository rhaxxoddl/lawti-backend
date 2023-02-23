package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.oli.HometownPolitician.global.argument.output.SlicePaginationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BillMessageRoomListDto {
    private List<BillMessageRoomDto> list;
    private SlicePaginationDto pagination;

    static public BillMessageRoomListDto from(List<BillUserRelation> billUserRelations, TargetSlicePaginationInput paginationInput) {
        return BillMessageRoomListDto.builder()
                .list(
                        billUserRelations.stream()
                                .map(BillMessageRoomDto::from)
                                .toList()
                )
                .pagination(SlicePaginationDto.from(paginationInput, billUserRelations.size()))
                .build();
    }
}
