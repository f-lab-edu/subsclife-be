package com.fthon.subsclife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionFacade {

    private final SubscribeService subscribeService;

    public synchronized void subscribe(Long userId, Long taskId) {
        subscribeService.subscribeTask(userId, taskId);
    }

    public void unsubscribe(Long userId, Long taskId) {
        subscribeService.unsubscribeTask(taskId, userId);
    }

}
