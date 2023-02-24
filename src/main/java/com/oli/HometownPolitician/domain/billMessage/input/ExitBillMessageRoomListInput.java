package com.oli.HometownPolitician.domain.billMessage.input;

import com.oli.HometownPolitician.domain.bill.input.BillInput;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class ExitBillMessageRoomListInput {
    @NotNull(message = "NULL이 들어올 수 없습니다")
    @Min(value = 1, message = "최소 1개 이상의 요소가 포함되어 있어야 합니다")
    private List<BillInput> list;
}
