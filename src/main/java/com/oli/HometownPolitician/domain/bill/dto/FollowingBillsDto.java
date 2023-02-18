package com.oli.HometownPolitician.domain.bill.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FollowingBillsDto {
    private List<BillDto> follwingBills;
}
