package com.oli.HometownPolitician.domain.bill.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BillDto {
    private Long billId;
    private String name;
}
