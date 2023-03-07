package com.oli.HometownPolitician.domain.committee.service;

import com.oli.HometownPolitician.domain.committee.dto.CommitteesDto;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class CommitteeService {
    private CommitteeRepository committeeRepository;
    CommitteesDto queryCommittees() {
        return CommitteesDto.from(committeeRepository.qFindAll());
    }
}
