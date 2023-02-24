package com.oli.HometownPolitician.domain.billMessage.service;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.input.BillInput;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageListDto;
import com.oli.HometownPolitician.domain.billMessage.dto.BillMessageRoomListDto;
import com.oli.HometownPolitician.domain.billMessage.dto.ExitMessageRoomResultDto;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageListInput;
import com.oli.HometownPolitician.domain.billMessage.input.BillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.input.ExitBillMessageRoomListInput;
import com.oli.HometownPolitician.domain.billMessage.repository.BillMessageRepository;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.billUserRelation.repository.BillUserRelationRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.equipment.UserPrefixEquipment;
import com.oli.HometownPolitician.domain.user.repository.UserRepository;
import com.oli.HometownPolitician.global.error.NotFoundError;
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
    private final UserRepository userRepository;
    private final BillRepository billRepository;

    public BillMessageRoomListDto queryBillMessageRoomList(BillMessageRoomListInput billMessageRoomListInput, String authorization) {
        List<BillUserRelation> billUserRelations = billUserRelationRepository.qFindByUserUuidAndFilter(
                billMessageRoomListInput,
                UserPrefixEquipment.deletePrefix(authorization)
        );

        return BillMessageRoomListDto.from(billUserRelations, billMessageRoomListInput.getPagination());
    }

    public BillMessageListDto queryBillMessageList(BillMessageListInput billMessageListInput) {
        List<BillMessage> billMessageList = billMessageRepository.qFindByBillId(billMessageListInput);

        return BillMessageListDto.from(billMessageList,billMessageListInput.getPagination());
    }

    public ExitMessageRoomResultDto exitMessageRooms(ExitBillMessageRoomListInput exitBillMessageRoomListInput, String authorization) {
        User user = userRepository.qFindByUuid(UserPrefixEquipment.deletePrefix(authorization)).orElseThrow(() -> new NotFoundError("해당 유저를 찾을 수 없습니다"));
        List<Long> unfollowBillIds = exitBillMessageRoomListInput.getList().stream().map(BillInput::getBillId).toList();
        List<Bill> unfollowBills = billRepository.queryBillsByIdList(unfollowBillIds);
        user.unfollowBills(unfollowBills);

        return ExitMessageRoomResultDto
                .builder()
                .isSuccess(true)
                .build();
    }
}
