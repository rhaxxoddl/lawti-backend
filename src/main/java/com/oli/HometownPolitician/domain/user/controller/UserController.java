package com.oli.HometownPolitician.domain.user.controller;

import com.oli.HometownPolitician.domain.user.dto.TokenDto;
import com.oli.HometownPolitician.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UserController {
    private final UserService userService;

    @QueryMapping("queryToken")
    public TokenDto queryToken() {
        return userService.createTokenDto(userService.createAnonymousUser());
    }
}
