package com.fthon.subsclife.controller;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.exception.ErrorInfo;
import com.fthon.subsclife.service.RemindService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/reminds")
@RequiredArgsConstructor
public class RemindController {

        private final RemindService remindService;

        //1. 회고 생성
        @PostMapping
        public ResponseEntity<HttpStatus> create(@RequestBody @Valid RemindDto.SaveRequest SaveRequest) {
                remindService.create(SaveRequest);

                return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        @GetMapping("/{remindId}")
        public ResponseEntity<RemindDto.DetailResponse> detail(
                @RequestHeader("user-id") Long userId,
                @PathVariable("remindId") Long remindId) {

                return new ResponseEntity<>(remindService.getRemindDetail(remindId), HttpStatus.OK);

        }


        @GetMapping
        @Operation(summary = "회고 목록 조회(히스토리)", description = "회고 목록 조회 시 사용되는 API")
        @Parameters({
                @Parameter(name = "start_date", description = "태스크의 시작 시간"),
                @Parameter(name = "end_date", description = "태스크의 종료 시간"),
                @Parameter(name = "remind_id", description = "회고 ID")
        })
        public ResponseEntity<PagedItem<RemindDto.ListResponse>> getRemindHistory(
                @RequestHeader("user-id") Long userId,
                @RequestParam(name = "remind_id", required = false) Long remindId,
                @RequestParam(name = "start_date", required = false) LocalDateTime startDate, // 태스크 시작 시간임
                @RequestParam(name = "end_date", required = false) LocalDateTime endDate, // 태스크 종료 시간임
                @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize) {

                RemindDto.Cursor cursor = getCursor(remindId, startDate, endDate, pageSize);

                return new ResponseEntity<>(remindService.getRemindList(cursor), HttpStatus.OK);
        }

        private static RemindDto.Cursor getCursor(Long remindId, LocalDateTime startDate, LocalDateTime endDate, Long pageSize) {
                return RemindDto.Cursor.builder()
                        .remindId(remindId)
                        .startDate(startDate)
                        .endDate(endDate)
                        .pageSize(pageSize)
                        .build();
        }
}
