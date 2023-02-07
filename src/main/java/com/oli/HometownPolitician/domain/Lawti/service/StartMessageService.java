package com.oli.HometownPolitician.domain.Lawti.service;

import com.oli.HometownPolitician.domain.Lawti.dto.StartMessageDto;
import com.oli.HometownPolitician.domain.Lawti.dto.StartMessagesDto;
import com.oli.HometownPolitician.domain.Lawti.repository.StartMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class StartMessageService {
    private final StartMessageRepository startMessageRepository;

    public StartMessagesDto getStartMessages() {
        return StartMessagesDto.from(startMessageRepository
                .findAll()
                .stream()
                .map(StartMessageDto::from)
                .collect(Collectors.toList())
        );
    }
}
