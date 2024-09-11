package com.fthon.subsclife.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RemindDto {

    @NoArgsConstructor
    @Getter
    public static class SaveRequest {

        @NotNull
        private Long taskId;

        @NotNull
        private Integer achievementRate;

        @NotNull
        private String achieveReason;

        @NotNull
        private String failReason;

        @NotNull
        private String improvementPlan;

        @Builder
        public SaveRequest(Long taskId, Integer achievementRate, String achieveReason, String failReason, String improvementPlan) {
            this.taskId = taskId;
            this.achievementRate = achievementRate;
            this.achieveReason = achieveReason;
            this.failReason = failReason;
            this.improvementPlan = improvementPlan;
        }

    }

    @NoArgsConstructor
    @Getter
    public static class DetailResponse {

        private Long remindId;
        private Integer achievementRate;
        private String achieveReason;
        private String failReason;
        private String improvementPlan;
        private TaskDto.ListResponse taskInfo;

        @Builder
        public DetailResponse(Long remindId, Integer achievementRate, String achieveReason, String failReason, String improvementPlan, TaskDto.ListResponse taskInfo) {
            this.remindId = remindId;
            this.achievementRate = achievementRate;
            this.achieveReason = achieveReason;
            this.failReason = failReason;
            this.improvementPlan = improvementPlan;
            this.taskInfo = taskInfo;
        }


    }

    @NoArgsConstructor
    @Getter
    public static class ListResponse {

        private TaskDto.DetailResponse DetailResponse;
    }


}
