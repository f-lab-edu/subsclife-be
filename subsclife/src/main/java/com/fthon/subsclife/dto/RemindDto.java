package com.fthon.subsclife.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RemindDto {

    @NoArgsConstructor
    @Getter
    public static class SaveRequest {

        @NotNull
        private Long userId;

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
        public SaveRequest(Long userId, Long taskId, Integer achievementRate, String achieveReason, String failReason, String improvementPlan) {
            this.userId = userId;
            this.taskId = taskId;
            this.achievementRate = achievementRate;
            this.achieveReason = achieveReason;
            this.failReason = failReason;
            this.improvementPlan = improvementPlan;
        }

    }
}
