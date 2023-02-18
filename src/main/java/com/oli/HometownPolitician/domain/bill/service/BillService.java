package com.oli.HometownPolitician.domain.bill.service;

import com.oli.HometownPolitician.domain.bill.dto.BillDetailDto;
import com.oli.HometownPolitician.domain.bill.dto.BillPdfUriDto;
import com.oli.HometownPolitician.domain.bill.dto.FollowingBillsDto;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.input.BillInput;
import com.oli.HometownPolitician.domain.bill.input.BillsInput;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.service.UserService;
import com.oli.HometownPolitician.global.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillService {
    private final BillRepository billRepository;
    private final UserService userService;

    public BillDetailDto queryBillDetail(BillInput billInput) {
        Bill bill = billRepository.findById(billInput.getBillId())
                .orElseThrow(()-> new NotFoundError("해당하는 법안이 존재하지 않습니다"));
        return BillDetailDto.from(bill);
    }
    public BillPdfUriDto queryBillPdfUri(BillInput billInput) {
        return null;
    }
    public List<Bill> queryBillsByIdList(BillsInput billsInput) {
        return billRepository.queryBillsByIdList(
                billsInput.getBillInputs()
                        .stream()
                        .map(BillInput::getBillId)
                        .toList()
        );
    }
    public FollowingBillsDto followBill(BillsInput billsInput, String authorization) {
        User user = userService.getUser(authorization);
        List<Bill> bills = queryBillsByIdList(billsInput);
        List<Bill> followBills = user.followBills(bills);
        return FollowingBillsDto.from(followBills);
    }
    public FollowingBillsDto unfollowBill(BillsInput billsInput, String authorization) {
        User user = userService.getUser(authorization);
        List<Bill> bills = queryBillsByIdList(billsInput);
        List<Bill> followBills = user.unfollowBills(bills);
        return FollowingBillsDto.from(followBills);
    }
}
