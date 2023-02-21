package com.oli.HometownPolitician.domain.billMessage.dto;

import com.oli.HometownPolitician.global.argument.output.SlicePaginationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BillMessageListDto {
    private List<MessageDto> list;
    private SlicePaginationDto pagination;
}
