package com.oli.HometownPolitician.domain.billMessage.service;

import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageListDto;
import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageRoomListDto;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageListInput;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.input.ExitBillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.repository.BillMessageRepository;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.billUserRelation.repository.BillUserRelationRepository;
import com.oli.HometownPolitician.domain.user.equipment.UserPrefixEquipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillMessageService {
    private final BillUserRelationRepository billUserRelationRepository;
    private final BillMessageRepository billMessageRepository;

    public BillMessageRoomListDto queryBillMessageRoomList(BillMessageRoomListInput billMessageRoomListInput, String authorization) {
        List<BillUserRelation> billUserRelations = billUserRelationRepository.qFindByUserUuidAndFilter(
                billMessageRoomListInput,
                UserPrefixEquipment.deletePrefix(authorization)
        );

        return BillMessageRoomListDto.from(billUserRelations, billMessageRoomListInput.getPagination());
    }

    public BillMessageListDto queryBillMessageList(BillMessageListInput billMessageListInput, String authorization) {
        return null;
    }

    public BillMessageRoomListDto exitMessageRoom(ExitBillMessageRoomListInput exitBillMessageRoomListInput, String authorization) {
        return null;
    }
}
