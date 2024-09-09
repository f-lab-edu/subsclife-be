package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.entity.Period;
import com.fthon.subsclife.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

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
}
