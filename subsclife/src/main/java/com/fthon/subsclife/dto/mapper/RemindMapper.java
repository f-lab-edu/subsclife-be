package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RemindMapper {

    private final TaskMapper taskMapper;

    public Remind toEntity(RemindDto.SaveRequest SaveRequest, Task task, User user) {
        return Remind.builder()
                .user(user)
                .task(task)
                .achievementRate(SaveRequest.getAchievementRate())
                .achieveReason(SaveRequest.getAchieveReason())
                .failReason(SaveRequest.getFailReason())
                .improvementPlan(SaveRequest.getImprovementPlan())
                .build();
    }


    public RemindDto.DetailResponse toDetailResponse(Remind remind, Task task) {
        return RemindDto.DetailResponse.builder()
                .remindId(remind.getId())
                .achievementRate(remind.getAchievementRate())
                .achieveReason(remind.getAchieveReason())
                .failReason(remind.getFailReason())
                .improvementPlan(remind.getImprovementPlan())
                .taskInfo(taskMapper.toListResponse(task))
                .build();
    }

    public RemindDto.ListResponse toListResponse(Remind remind) {
        return RemindDto.ListResponse.builder()
                .remindContent(
                        RemindDto.Content.builder()
                                .remindId(remind.getId())
                                .achievementRate(remind.getAchievementRate())
                                .achieveReason(remind.getAchieveReason())
                                .failReason(remind.getFailReason())
                                .improvementPlan(remind.getImprovementPlan())
                                .build()
                )
                .taskInfo(taskMapper.toListResponse(remind.getTask()))
                .build();
    }
}
