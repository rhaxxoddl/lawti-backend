package com.pivot.hp.hometownpolitician.controller;

import com.pivot.hp.hometownpolitician.entity.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class SampleController {

    @GetMapping("/sample")
    public String sampleCall(@RequestParam(required = false) String args) {
        return "sample";
    }

    @QueryMapping(value = "findAll")
    public List<Sample> findAll() {
        List<Sample> sampleList = new ArrayList<>(10);
        IntStream.range(0, 10).forEach(e -> {
            sampleList.add(new Sample(new Long(e)));
        });
        return sampleList;
    }

    @QueryMapping(value = "findById")
    public Optional<Sample> findById(@Argument Long id) {
        Sample sample = new Sample(id);
        return Optional.ofNullable(sample);
    }

}
