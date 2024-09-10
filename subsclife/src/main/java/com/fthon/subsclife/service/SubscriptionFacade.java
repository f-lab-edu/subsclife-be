package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionFacade {

    private final SubscribeService subscribeService;

    private final UserMapper userMapper;

    public synchronized void subscribe(Long userId, Long taskId) {
        subscribeService.subscribeTask(userId, taskId);
    }

    public void unsubscribe(Long userId, Long taskId) {
        subscribeService.unsubscribeTask(taskId, userId);
    }

    public List<UserDto.SubscribedTaskResponse> getSubscribedTasks(Long userId) {
        return sortTaskByDate(subscribeService.findSubscribes(userId));
    }

    private List<UserDto.SubscribedTaskResponse> sortTaskByDate(List<Subscribe> subscribes) {
        return subscribes.stream()
                .sorted(Comparator
                        .comparing((Subscribe subscribe) -> subscribe.getTask().getPeriod().getEndDate())
                        .thenComparing(subscribe -> subscribe.getTask().getPeriod().getStartDate()))
                .map(subscribe -> userMapper.toSubscribedTaskResponse(subscribe.getTask()))
                .collect(Collectors.toList());
    }
}
