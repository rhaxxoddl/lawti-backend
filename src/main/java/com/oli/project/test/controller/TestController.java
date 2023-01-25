package com.oli.project.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @QueryMapping(name = "ThisIsForBaseQueryDoNotTouchThisMethod")
    public void defaultQuery() {
        System.out.println("true = " + true);
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping(name = "ThisIsForBaseMutationDoNotTouchThisMethod")
    public void defaultMutation() {
        System.out.println("true = " + true);
    }

}
