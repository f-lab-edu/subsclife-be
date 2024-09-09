package com.fthon.subsclife.exception;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private final String type;
    private final String message;

    public ErrorInfo(Throwable e) {
        this.type = e.getClass().getName();
        this.message = e.getMessage();
    }
}
