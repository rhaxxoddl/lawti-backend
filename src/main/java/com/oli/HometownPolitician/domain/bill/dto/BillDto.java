package com.oli.HometownPolitician.domain.bill.dto;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BillDto {
    private Long billId;
    private String title;

    static public BillDto from(Bill bill) {
        return bill == null ? null : BillDto.builder()
                .billId(bill.getId())
                .title(bill.getTitle())
                .build();
    }
}
