package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BillDto {
    private Long billId;
    private String title;

    static public BillDto from(Bill billEntity) {
        return BillDto.builder()
                .billId(billEntity.getId())
                .title(billEntity.getTitle())
                .build();
    }
}
