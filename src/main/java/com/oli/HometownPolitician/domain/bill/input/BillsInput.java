package com.oli.HometownPolitician.domain.bill.input;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
public class BillsInput {
    @NotNull
    private List<BillInput> list;
}
