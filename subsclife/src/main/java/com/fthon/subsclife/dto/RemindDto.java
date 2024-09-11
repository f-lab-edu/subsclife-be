package com.fthon.subsclife.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    public static class SingleResponse {

        private UserDto.Response userInfo;
        private Content remindContent;

        @Builder
        public SingleResponse(UserDto.Response userInfo, Content remindContent) {
            this.userInfo = userInfo;
            this.remindContent = remindContent;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class ListResponse {

        private Content remindContent;
        private TaskDto.ListResponse taskInfo;

        @Builder
        public ListResponse(Content remindContent, TaskDto.ListResponse taskInfo) {
            this.remindContent = remindContent;
            this.taskInfo = taskInfo;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class Content {
        //TODO: remindId를 제거하고 사용하는 부분에서 선언하기
        private Long remindId;
        private Integer achievementRate;
        private String achieveReason;
        private String failReason;
        private String improvementPlan;

        @Builder
        public Content(Long remindId, Integer achievementRate, String achieveReason, String failReason, String improvementPlan) {
            this.remindId = remindId;
            this.achievementRate = achievementRate;
            this.achieveReason = achieveReason;
            this.failReason = failReason;
            this.improvementPlan = improvementPlan;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class Cursor {
        private Long remindId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Long pageSize;

        @Builder
        public Cursor(Long remindId, LocalDateTime startDate, LocalDateTime endDate, Long pageSize) {
            this.remindId = remindId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.pageSize = pageSize;
        }
    }

}
