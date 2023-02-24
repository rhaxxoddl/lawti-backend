package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.global.argument.input.TargetSlicePaginationInput;
import com.oli.HometownPolitician.global.argument.output.SlicePaginationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BillMessageListDto {
    private List<MessageDto> list;
    private SlicePaginationDto pagination;

    static public BillMessageListDto from(List<BillMessage> billMessageList, TargetSlicePaginationInput paginationInput) {
        return BillMessageListDto.builder()
                .list(
                        billMessageList.stream()
                                .map(MessageDto::from)
                                .toList()
                )
                .pagination(SlicePaginationDto.from(paginationInput, billMessageList.size()))
                .build();
    }
}
