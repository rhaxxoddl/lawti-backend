package com.oli.HometownPolitician.domain.startMessage.repository;

import com.oli.HometownPolitician.domain.startMessage.entity.StartMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StartMessageRepositoryTest {
    @Autowired
    private StartMessageRepository startMessageRepository;

    private final String MESSAGE1 = "안녕하세요!";
    private final String MESSAGE2 = "저는 로티라고 해요.";
    private final String MESSAGE3 = "새로운 법안이 올라오거나 법이 바뀔 때 알려드릴게요.";
    private final String MESSAGE4 = "관심분야를 알려주시면 관련된 법안을 알려드릴게요.";
    private final int START_MESSAGE_SIZE = 4;

    @BeforeEach
    private void initialData() {
        List<StartMessage> startMessageList = new ArrayList<>();
        startMessageList.add(new StartMessage(1L, MESSAGE1));
        startMessageList.add(new StartMessage(2L, MESSAGE2));
        startMessageList.add(new StartMessage(3L, MESSAGE3));
        startMessageList.add(new StartMessage(4L, MESSAGE4));
        startMessageRepository.saveAll(startMessageList);
    }

    @AfterEach
    private void cleanData() {
        startMessageRepository.deleteAll();
    }

    @Test
    @DisplayName("Lawti 시작 메시지가 잘 나오는지 확인")
    public void start_message_find_all_well_test() {
        List<StartMessage> startMessageList = startMessageRepository.findAll();
        assertThat(startMessageList.size()).isEqualTo(START_MESSAGE_SIZE);
        assertThat(startMessageList.get(0).getMessage()).isEqualTo(MESSAGE1);
        assertThat(startMessageList.get(1).getMessage()).isEqualTo(MESSAGE2);
        assertThat(startMessageList.get(2).getMessage()).isEqualTo(MESSAGE3);
        assertThat(startMessageList.get(3).getMessage()).isEqualTo(MESSAGE4);
    }

}