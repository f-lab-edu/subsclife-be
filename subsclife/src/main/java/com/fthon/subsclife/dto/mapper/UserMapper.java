package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto.Response toResponseDto(User user) {
        return UserDto.Response.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }

    public UserDto.SubscribedTaskResponse toSubscribedTaskResponse(Task task) {
        return UserDto.SubscribedTaskResponse.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .simpleInfo(task.getSimpleInfo())
                .startDate(task.getPeriod().getStartDate())
                .endDate(task.getPeriod().getEndDate())
                .build();
    }
}
