package com.fthon.subsclife.exception;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private final String errorType;
    private final String message;

    public ErrorInfo(Throwable e) {
        this.errorType = e.getClass().getSimpleName();
        this.message = e.getMessage();
    }
}
