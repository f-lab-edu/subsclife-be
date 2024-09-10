package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.entity.Period;
import com.fthon.subsclife.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserMapper userMapper;

    public Task toEntity(TaskDto.SaveRequest saveRequest) {
        return Task.builder()
                .title(saveRequest.getTitle())
                .simpleInfo(saveRequest.getSimpleInfo())
                .detail(saveRequest.getDetail())
                .period(Period.builder()
                        .startDate(saveRequest.getStartDate())
                        .endDate(saveRequest.getEndDate())
                        .build()
                )
                .build();
    }

    public TaskDto.DetailResponse toDetailResponse(Task task, Long userId) {
        return TaskDto.DetailResponse.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .simpleInfo(task.getSimpleInfo())
                .detail(task.getDetail())
                .startDate(task.getPeriod().getStartDate())
                .endDate(task.getPeriod().getEndDate())
                .subscribers(
                        task.getSubscribes().stream().map(t -> userMapper.toResponseDto(t.getUser())).toList()
                )
                .isSubscribed(task.isSubscribed(userId))
                .build();
    }
}
