package com.fthon.subsclife.exception.advice;

import com.fthon.subsclife.exception.ErrorInfo;
import com.fthon.subsclife.exception.TaskSubscriptionClosedException;
import com.fthon.subsclife.exception.UserSubscriptionOverflowException;
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

    @ExceptionHandler(TaskSubscriptionClosedException.class)
    public ResponseEntity<ErrorInfo> TaskSubscriptionClosedExceptionHandler(TaskSubscriptionClosedException e) {
        return new ResponseEntity<>(new ErrorInfo(e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserSubscriptionOverflowException.class)
    public ResponseEntity<ErrorInfo> UserSubscriptionOverflowExceptionHandler(UserSubscriptionOverflowException e) {
        return new ResponseEntity<>(new ErrorInfo(e), HttpStatus.CONFLICT);
    }
}
