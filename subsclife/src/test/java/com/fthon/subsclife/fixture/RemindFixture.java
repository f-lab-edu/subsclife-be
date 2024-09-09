package com.fthon.subsclife.fixture;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.entity.Remind;

public class RemindFixture {

    private static final String userId = "4";
    private static final String taskId = "4";
    private static final String achievementRate = "80";
    private static final String achieveReason = "achieveReason";
    private static final String failReason = "failReason";
    private static final String improvementPlan = "improvementPlan";


    public static RemindDto.RemindRequest remind_fixture_with_all_info() {
        return RemindDto.RemindRequest.builder()
                .userId(Long.parseLong(userId))
                .taskId(Long.parseLong(taskId))
                .achievementRate(Integer.parseInt(achievementRate))
                .achieveReason(achieveReason)
                .failReason(failReason)
                .improvementPlan(improvementPlan)
                .build();
    }

    public static Remind remind_with_all_info() {
        return Remind.builder()
                .userId(Long.parseLong(userId))
                .taskId(Long.parseLong(taskId))
                .achievementRate(Integer.parseInt(achievementRate))
                .achieveReason(achieveReason)
                .failReason(failReason)
                .improvementPlan(improvementPlan)
                .build();
    }

    public static RemindDto.RemindRequest remind_fixture_without_achievementRate() {
        return RemindDto.RemindRequest.builder()
                .userId(Long.parseLong(userId))
                .taskId(Long.parseLong(taskId))
                .achieveReason(achieveReason)
                .failReason(failReason)
                .improvementPlan(improvementPlan)
                .build();
    }

    public static RemindDto.RemindRequest remind_fixture_without_achieveReason() {
        return RemindDto.RemindRequest.builder()
                .userId(Long.parseLong(userId))
                .taskId(Long.parseLong(taskId))
                .achievementRate(Integer.parseInt(achievementRate))
                .failReason(failReason)
                .improvementPlan(improvementPlan)
                .build();
    }

    public static RemindDto.RemindRequest remind_fixture_without_failReason() {
        return RemindDto.RemindRequest.builder()
                .userId(Long.parseLong(userId))
                .taskId(Long.parseLong(taskId))
                .achievementRate(Integer.parseInt(achievementRate))
                .achieveReason(achieveReason)
                .improvementPlan(improvementPlan)
                .build();
    }

    public static RemindDto.RemindRequest remind_fixture_without_improvementPlan() {
        return RemindDto.RemindRequest.builder()
                .userId(Long.parseLong(userId))
                .taskId(Long.parseLong(taskId))
                .achievementRate(Integer.parseInt(achievementRate))
                .achieveReason(achieveReason)
                .failReason(failReason)
                .build();
    }

}
