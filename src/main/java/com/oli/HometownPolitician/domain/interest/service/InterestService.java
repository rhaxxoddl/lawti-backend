package com.oli.HometownPolitician.domain.interest.service;

import com.oli.HometownPolitician.domain.interest.dto.InterestsDto;
import com.oli.HometownPolitician.domain.interest.dto.InterestsInput;
import com.oli.HometownPolitician.domain.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class InterestService {
    private final InterestRepository interestRepository;
    public InterestsDto queryInterests() {
        return InterestsDto
                .from(interestRepository.findAll());
    }
    public void queryFollowedMyInterests(InterestsInput interestsInput) {
    }
    public void followMyInterests(InterestsInput interestsInput) {
    }
    public void unfollowMyInterests(InterestsInput interestsInput) {
    }

}
