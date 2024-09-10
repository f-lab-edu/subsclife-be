package com.fthon.subsclife.service;


import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.dto.mapper.TaskMapper;
import com.fthon.subsclife.entity.Subscribe;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final LoginService loginService;

    @Transactional
    public void saveTask(TaskDto.SaveRequest dto) {
        Task task = taskMapper.toEntity(dto);

        taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public TaskDto.DetailResponse getTaskDetail(Long taskId) {
        Task task = findTaskByIdWithSubscribesAndUser(taskId);
        Long userId = loginService.getLoginUserId();

        return taskMapper.toDetailResponse(task, userId);
    }

    @Transactional(readOnly = true)
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("찾으려는 태스크가 없습니다."));
    }

    @Transactional(readOnly = true)
    public Task findTaskByIdWithSubscribes(Long taskId) {
        return taskRepository.findByIdWithSubscribes(taskId)
                .orElseThrow(() -> new NoSuchElementException("찾으려는 태스크가 없습니다."));
    }

    @Transactional(readOnly = true)
    public Task findTaskByIdWithSubscribesAndUser(Long taskId) {
        return taskRepository.findByIdWithSubscribesAndUser(taskId)
                .orElseThrow(() -> new NoSuchElementException("찾으려는 태스크가 없습니다."));
    }

}
