package com.oli.HometownPolitician.domain.bill.input;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class BillInput {
    @NotNull
    @Min(1L)
    private Long billId;
}
