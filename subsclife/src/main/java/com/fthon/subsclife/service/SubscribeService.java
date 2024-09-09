package com.fthon.subsclife.service;


import com.fthon.subsclife.entity.Subscribe;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    private final UserService userService;

    private final TaskService taskService;


    @Transactional
    public void subscribeTask(Long userId, Long taskId) {
        User user = userService.findUserByIdWithSubscribes(userId);
        Task task = taskService.findTaskByIdWithSubscribes(taskId);

        Subscribe subscribe = user.subscribe(task);
        subscribeRepository.save(subscribe);
    }
}
