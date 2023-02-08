package com.oli.HometownPolitician.domain.startMessage.service;

import com.oli.HometownPolitician.domain.startMessage.dto.StartMessageDto;
import com.oli.HometownPolitician.domain.startMessage.dto.StartMessagesDto;
import com.oli.HometownPolitician.domain.startMessage.entity.StartMessage;
import com.oli.HometownPolitician.domain.startMessage.repository.StartMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class StartMessageService {
    private final StartMessageRepository startMessageRepository;

    public StartMessagesDto getStartMessages() {
        List<StartMessage> startMessageList = new ArrayList<>();
        startMessageList.add(0, new StartMessage(0L, "안녕하세요!"));
        startMessageList.add(1, new StartMessage(1L, "저는 로티라고 해요."));
        startMessageList.add(2, new StartMessage(2L, "새로운 법안이 올라오거나 법이 바뀔 때 알려드릴게요."));
        startMessageList.add(3, new StartMessage(3L, "관심분야를 알려주시면 관련된 법안을 알려드릴게요."));
        return StartMessagesDto.from(startMessageList
                .stream()
                .map(StartMessageDto::from)
                .collect(Collectors.toList())
        );
//        return StartMessagesDto.from(startMessageRepository
//                .findAll()
//                .stream()
//                .map(StartMessageDto::from)
//                .collect(Collectors.toList())
//        );
    }
}
