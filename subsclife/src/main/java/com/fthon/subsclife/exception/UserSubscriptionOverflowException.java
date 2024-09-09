package com.fthon.subsclife.exception;


/**
 * 사용자의 구독이 최대치에 도달했을 때
 */
public class UserSubscriptionOverflowException extends RuntimeException {
    public UserSubscriptionOverflowException() {
        super();
    }

    public UserSubscriptionOverflowException(String message) {
        super(message);
    }
}
