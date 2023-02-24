package com.oli.HometownPolitician.domain.billMessage.controller;

import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageListDto;
import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageRoomListDto;
import com.oli.HometownPolitician.domain.billMessage.dto.ExitMessageRoomResultDto;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageListInput;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.input.ExitBillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.service.BillMessageService;
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
public class BillMessageController {
    private final BillMessageService billMessageService;
    @QueryMapping("queryBillMessageRoomList")
    public BillMessageRoomListDto queryBillMessageRoomList(@Argument(name = "input") @Valid BillMessageRoomListInput billMessageRoomListInput, @ContextValue String authorization) {
        return billMessageService.queryBillMessageRoomList(billMessageRoomListInput, authorization);
    }
    @QueryMapping("queryBillMessageList")
    public BillMessageListDto queryBillMessageList(@Argument(name = "input") @Valid BillMessageListInput billMessageListInput, @ContextValue String authorization) {
        return billMessageService.queryBillMessageList(billMessageListInput);
    }
    @MutationMapping("exitMessageRooms")
    public ExitMessageRoomResultDto exitMessageRooms(@Argument(name = "input") @Valid ExitBillMessageRoomListInput exitBillMessageRoomListInput, @ContextValue String authorization) {
        return billMessageService.exitMessageRooms(exitBillMessageRoomListInput, authorization);
    }
}
