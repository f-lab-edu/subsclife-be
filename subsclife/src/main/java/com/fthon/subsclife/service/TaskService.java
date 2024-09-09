package com.fthon.subsclife.service;


import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.dto.mapper.TaskMapper;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Transactional
    public void saveTask(TaskDto.SaveRequest dto) {
        Task task = taskMapper.toEntity(dto);

        taskRepository.save(task);
    }
}
