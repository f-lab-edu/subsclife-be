package com.fthon.subsclife.service;


import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskService taskService;

    private final SubscribeService subscribeService;
    private final LoginService loginService;


    @Transactional
    public void saveTaskThenSubscribe(TaskDto.SaveRequest saveRequest) {
        subscribeService.subscribeTask(loginService.getLoginUserId(), taskService.saveTask(saveRequest));
    }
}
