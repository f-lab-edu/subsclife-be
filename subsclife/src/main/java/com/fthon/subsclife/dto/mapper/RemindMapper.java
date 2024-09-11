package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.entity.Remind;
import org.springframework.stereotype.Component;

@Component
public class RemindMapper {

    public Remind toEntity(RemindDto.SaveRequest SaveRequest) {
        return Remind.builder()
                .userId(SaveRequest.getUserId())
                .taskId(SaveRequest.getTaskId())
                .achievementRate(SaveRequest.getAchievementRate())
                .achieveReason(SaveRequest.getAchieveReason())
                .failReason(SaveRequest.getFailReason())
                .improvementPlan(SaveRequest.getImprovementPlan())
                .build();
    }

}
