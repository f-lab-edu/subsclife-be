package com.fthon.subsclife.dto;

import com.fthon.subsclife.entity.Period;
import com.fthon.subsclife.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDto {

    @NoArgsConstructor
    @Getter
    public static class SaveRequest {

        @NotBlank
        private String title;

        private String simpleInfo;

        private String detail;

        @NotNull
        private LocalDateTime startDate;

        @NotNull
        private LocalDateTime endDate;

        @Builder
        public SaveRequest(String title, String simpleInfo, String detail, LocalDateTime startDate, LocalDateTime endDate) {
            this.title = title;
            this.simpleInfo = simpleInfo;
            this.detail = detail;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class Cursor {
        private Long taskId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        @Builder
        public Cursor(Long taskId, LocalDateTime startDate, LocalDateTime endDate) {
            this.taskId = taskId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class SearchCondition {
        private LocalDateTime startFrom;
        private LocalDateTime endTo;
        private String keyword;
        private Long pageSize;

        @Builder
        public SearchCondition(LocalDateTime startFrom, LocalDateTime endTo, String keyword, Long pageSize) {
            this.startFrom = startFrom;
            this.endTo = endTo;
            this.keyword = keyword;
            this.pageSize = pageSize;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class DetailResponse {

        private Long taskId;
        private String title;
        private String simpleInfo;
        private String detail;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private List<UserDto.Response> subscribers;
        private Boolean isSubscribed;

        @Builder
        public DetailResponse(Long taskId, String title, String simpleInfo, String detail, List<UserDto.Response> subscribers,
                              LocalDateTime startDate, LocalDateTime endDate, Boolean isSubscribed) {
            this.taskId = taskId;
            this.title = title;
            this.simpleInfo = simpleInfo;
            this.detail = detail;
            this.subscribers = subscribers;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isSubscribed = isSubscribed;
        }
    }


    @NoArgsConstructor
    @Getter
    public static class ListResponse {
        private Long taskId;
        private String title;
        private String simpleInfo;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Long subscriberCount;

        @Builder
        public ListResponse(Long taskId, String title, String simpleInfo,
                            LocalDateTime startDate, LocalDateTime endDate, Long subscriberCount) {
            this.taskId = taskId;
            this.title = title;
            this.simpleInfo = simpleInfo;
            this.startDate = startDate;
            this.endDate = endDate;
            this.subscriberCount = subscriberCount;
        }
    }


    // 회고와 함께 조회되는 (이미 종료된) 태스크용
    @NoArgsConstructor
    @Getter
    public static class HistoryResponse {

        private Long taskId;
        private String title;
        private String simpleInfo;
        private String detail;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        List<RemindDto.SingleResponse> reminds;

        @Builder
        public HistoryResponse(Long taskId, String title, String simpleInfo, String detail,
                               LocalDateTime startDate, LocalDateTime endDate, List<RemindDto.SingleResponse> reminds) {
            this.taskId = taskId;
            this.title = title;
            this.simpleInfo = simpleInfo;
            this.detail = detail;
            this.startDate = startDate;
            this.endDate = endDate;
            this.reminds = reminds;
        }
    }
}
