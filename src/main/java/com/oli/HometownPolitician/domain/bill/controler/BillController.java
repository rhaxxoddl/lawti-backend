package com.oli.HometownPolitician.domain.bill.controler;

import com.oli.HometownPolitician.domain.bill.dto.BillDetailDto;
import com.oli.HometownPolitician.domain.bill.dto.BillPdfUriDto;
import com.oli.HometownPolitician.domain.bill.dto.FollowingBillsDto;
import com.oli.HometownPolitician.domain.bill.input.BillInput;
import com.oli.HometownPolitician.domain.bill.input.BillsInput;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class BillController {

    @QueryMapping(name = "queryBillDetail")
    public BillDetailDto queryBillDetail(@Argument(name = "input") @Valid BillInput billInput) {
        return null;
    }
    @QueryMapping(name = "queryBillPdfUri")
    public BillPdfUriDto queryBillPdfUri(@Argument(name = "input") @Valid BillInput billInput) {
        return null;
    }
    @MutationMapping(name = "followBills")
    public FollowingBillsDto followBills(@Argument(name = "input") @Valid BillsInput billInput, @ContextValue String authorization) {
        return null;
    }
    @MutationMapping(name = "unfollowBills")
    public FollowingBillsDto unfollowBills(@Argument(name = "input") @Valid BillsInput billInput, @ContextValue String authorization) {
        return null;
    }
}
