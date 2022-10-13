package com.pivot.hp.hometownpolitician.controller;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
public class SampleController {
    @GetMapping("/sample")
    public String sampleGetCall(@RequestParam String args, @RequestParam String jseo) {
        return "hello";
    }

    @PostMapping("/sample")
    public String samplePostCall(@RequestBody @Valid SampleDto dto) {
        return "hello";
    }

    @Data
    static class SampleDto {
        @NotEmpty String args;

        @NotEmpty @Length(min = 1, max = 4) String name;
    }
}
