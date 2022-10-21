package com.pivot.hp.hometownpolitician.controller;

import com.pivot.hp.hometownpolitician.entity.User;
import com.pivot.hp.hometownpolitician.repository.UserRepository;
import com.pivot.hp.hometownpolitician.service.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Data
    static class LoginDto {

        @NotNull
        @NotEmpty
        @Email
        private String email;

        @NotNull
        @NotEmpty
        @Size(min = 4, max = 8)
        private String password;

    }

    @Data
    static class DummyDto {

        @NotNull
        @NotEmpty
        @Email
        private String email;

        @NotNull
        @NotEmpty
        @Size(min = 1, max = 8)
        private String nickname;

        @NotNull
        @NotEmpty
        @Size(min = 4, max = 8)
        private String password;

    }

    @PostMapping("/login")
    public String loginCall(@RequestBody @Valid LoginDto dto) {
        String token = userService.login(dto.getEmail(), dto.getPassword());
        return "login " + token;
    }

    @PostMapping("/signup")
    public String signupCall(@RequestBody @Valid DummyDto dto) {
        userService.signup(new User(dto.getEmail(), dto.getNickname(), dto.getPassword()));
        return "signup";
    }

    @PostMapping("/dummy")
    public User dummyCall(@RequestBody @Valid DummyDto dto) {
        User user = new User(dto.getEmail(), dto.getNickname(), dto.getPassword());
        return userRepository.save(user);
    }

}
