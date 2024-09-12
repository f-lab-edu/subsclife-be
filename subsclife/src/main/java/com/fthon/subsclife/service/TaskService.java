package com.fthon.subsclife.service;


import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.dto.mapper.TaskMapper;
import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.Subscribe;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final UserMapper userMapper;

    private final RemindMapper remindMapper;

    private final LoginService loginService;

    private final UserService userService;


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

    /**
     * 종료된 태스크의 참여자를 조회합니다.
     * 구독자와 회고를 작성한 사용자를 포함합니다.
     */
    @Transactional(readOnly = true)
    public List<UserDto.Response> getSubscriberList(Long taskId) {
        Task task = findTaskByIdWithSubscribesAndUser(taskId);

        List<User> users = userService.findUserByTaskIdWhoReminded(taskId);

        users.addAll(task.getSubscribes()
                .stream()
                .map(Subscribe::getUser).toList());

        return users.stream()
                .map(userMapper::toResponseDto)
                .toList();
    }



    @Transactional(readOnly = true)
    public PagedItem<TaskDto.ListResponse> getTaskList(TaskDto.Cursor cursor, TaskDto.SearchCondition cond) {
        PagedItem<Task> pagedTasks = taskRepository.searchTaskList(cursor, cond);

        List<TaskDto.ListResponse> taskListResponses = pagedTasks.getItems()
                .stream()
                .map(taskMapper::toListResponse)
                .toList();

        return new PagedItem<>(taskListResponses, pagedTasks.getHasNext());
    }

    @Transactional(readOnly = true)
    public TaskDto.HistoryResponse getTaskHistory(Long taskId) {
        Task task = findTaskByIdWithRemindsAndUser(taskId);

        List<RemindDto.SingleResponse> remindList = task.getReminds().stream()
                .map(r -> remindMapper.toSingleResponse(r, userMapper.toResponseDto(r.getUser())))
                .toList();

        return taskMapper.toHistoryResponse(task, remindList);
    }


    // TODO: 반복되는 단순 조회 메소드 중복 제거하기
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

    @Transactional(readOnly = true)
    public Task findTaskByIdWithRemindsAndUser(Long taskId) {
        return taskRepository.findByIdWithRemindsAndUsers(taskId)
                .orElseThrow(() -> new NoSuchElementException("찾으려는 태스크가 없습니다."));
    }

}
