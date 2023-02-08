package com.oli.HometownPolitician.domain.startMessage.controller;


import com.oli.HometownPolitician.domain.startMessage.dto.StartMessagesDto;
import com.oli.HometownPolitician.domain.startMessage.service.StartMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class StartMessageController {
    private final StartMessageService startMessageService;

    @QueryMapping(name = "queryStartMessages")
    public StartMessagesDto getStartMessages() {
        return startMessageService.getStartMessages();
    }
}
