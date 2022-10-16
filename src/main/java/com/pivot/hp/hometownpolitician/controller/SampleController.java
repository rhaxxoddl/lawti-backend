package com.pivot.hp.hometownpolitician.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/sample")
    public String sampleCall(@RequestParam String args) {
        return "sample";
    }
}
