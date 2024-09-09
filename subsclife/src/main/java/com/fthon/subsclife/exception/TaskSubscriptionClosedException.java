package com.fthon.subsclife.exception;


/**
 * 태스크에 대한 구독자 수가 초과됐을 때 발생
 */
public class TaskSubscriptionClosedException extends RuntimeException {

    public TaskSubscriptionClosedException() {
        super();
    }

    public TaskSubscriptionClosedException(String message) {
        super(message);
    }
}
