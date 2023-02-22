package com.oli.HometownPolitician.domain.billMessage.service;

import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageListDto;
import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageRoomListDto;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageListInput;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.input.ExitBillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billUserRelation.repository.BillUserRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillMessageService {
    private final BillUserRelationRepository billUserRelationRepository;

    public BillMessageRoomListDto queryBillMessageRoomList(BillMessageRoomListInput billMessageRoomListInput, String authorization) {
//        List<BillUserRelation> billUserRelations = billUserRelationRepository
        return null;
    }

    public BillMessageListDto queryBillMessageList(BillMessageListInput billMessageListInput, String authorization) {
        return null;
    }

    public BillMessageRoomListDto exitMessageRoom(ExitBillMessageRoomListInput exitBillMessageRoomListInput, String authorization) {
        return null;
    }
}
