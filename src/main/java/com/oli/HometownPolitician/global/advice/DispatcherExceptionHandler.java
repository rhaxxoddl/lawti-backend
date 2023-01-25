package com.oli.HometownPolitician.global.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DispatcherExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<String> handleMultipartExceptionException(HttpServletRequest request, Throwable ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("""
                {
                    "errors": [
                        {
                            "message": "file cannot be uploaded, please reduce the size",
                            "locations": [
                                {
                                    "line": 2,
                                    "column": 5
                                }
                            ],
                            "path": [
                                "handleMultipartExceptionException"
                            ],
                            "extensions": {
                                "status": "FailedError"
                                "classification": "FORBIDDEN"
                            }
                        }
                    ],
                    "data": null
                }
                """, headers, HttpStatus.OK);
    }

}
