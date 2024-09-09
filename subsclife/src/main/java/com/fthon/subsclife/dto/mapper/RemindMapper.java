package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.entity.Remind;
import org.springframework.stereotype.Component;

@Component
public class RemindMapper {

    public Remind toEntity(RemindDto.RemindRequest remindRequest) {
        return Remind.builder()
                .userId(remindRequest.getUserId())
                .taskId(remindRequest.getTaskId())
                .achievementRate(remindRequest.getAchievementRate())
                .achieveReason(remindRequest.getAchieveReason())
                .failReason(remindRequest.getFailReason())
                .improvementPlan(remindRequest.getImprovementPlan())
                .build();
    }

}
