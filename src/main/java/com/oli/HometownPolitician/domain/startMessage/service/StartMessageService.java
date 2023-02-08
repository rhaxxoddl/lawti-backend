package com.oli.HometownPolitician.domain.startMessage.service;

import com.oli.HometownPolitician.domain.startMessage.dto.StartMessageDto;
import com.oli.HometownPolitician.domain.startMessage.dto.StartMessagesDto;
import com.oli.HometownPolitician.domain.startMessage.repository.StartMessageRepository;
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

    public StartMessagesDto queryStartMessages() {
        return StartMessagesDto.from(startMessageRepository
                .findAll()
                .stream()
                .map(StartMessageDto::from)
                .collect(Collectors.toList())
        );
    }
}
