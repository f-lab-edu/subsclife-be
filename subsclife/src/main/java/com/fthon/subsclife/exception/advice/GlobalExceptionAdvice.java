package com.fthon.subsclife.exception.advice;

import com.fthon.subsclife.exception.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorInfo> NoSuchElementExceptionHandler(NoSuchElementException e) {
        return new ResponseEntity<>(new ErrorInfo(e), HttpStatus.NOT_FOUND);
    }
}
