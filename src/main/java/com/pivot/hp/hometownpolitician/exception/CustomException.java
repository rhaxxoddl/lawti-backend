package com.pivot.hp.hometownpolitician.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final String message;

    private final HttpStatus httpStatus;

}
