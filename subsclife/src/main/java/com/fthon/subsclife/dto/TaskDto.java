package com.fthon.subsclife.dto;

import com.fthon.subsclife.entity.Period;
import com.fthon.subsclife.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
