package com.fthon.subsclife.fixture;

import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.entity.Period;
import com.fthon.subsclife.entity.Task;

import java.time.LocalDateTime;

public class TaskFixture {

    private static final String title = "test title";
    private static final String simpleInfo = "test simple info";
    private static final String detail = "test detail";
    private static final LocalDateTime startDate = LocalDateTime.now();
    private static final LocalDateTime endDate = LocalDateTime.now().plusDays(1);

    public static TaskDto.SaveRequest task_fixture_with_all_info() {
        return TaskDto.SaveRequest.builder()
                .title(title)
                .simpleInfo(simpleInfo)
                .detail(detail)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public static Task task_with_all_info() {
        return Task.builder()
                .title(title)
                .simpleInfo(simpleInfo)
                .detail(detail)
                .period(
                    Period.builder()
                            .startDate(startDate)
                            .endDate(endDate)
                            .build()
                )
                .build();
    }

    public static TaskDto.SaveRequest task_fixture_without_detail_info() {
        return TaskDto.SaveRequest.builder()
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public static TaskDto.SaveRequest task_fixture_without_title() {
        return TaskDto.SaveRequest.builder()
                .simpleInfo(simpleInfo)
                .detail(detail)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}
