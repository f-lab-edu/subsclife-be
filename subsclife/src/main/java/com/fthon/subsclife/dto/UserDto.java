package com.fthon.subsclife.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String nickname;

        @Builder
        public Response(Long id, String name, String nickname) {
            this.id = id;
            this.name = name;
            this.nickname = nickname;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class SubscribedTaskResponse {

        private Long taskId;
        private String title;
        private String simpleInfo;
        private Long subscriberCount;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        @Builder
        public SubscribedTaskResponse(Long taskId, String title, String simpleInfo,
                                      Long subscriberCount, LocalDateTime startDate, LocalDateTime endDate) {
            this.taskId = taskId;
            this.title = title;
            this.simpleInfo = simpleInfo;
            this.subscriberCount = subscriberCount;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
