package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FollowingBillsDto {
    private List<BillDto> list;

    static public FollowingBillsDto from(List<Bill> bills) {
        return FollowingBillsDto.builder()
                .list(
                        bills.stream()
                                .map(BillDto::from)
                                .toList()
                )
                .build();
    }
}
