package com.fthon.subsclife.controller;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.exception.ErrorInfo;
import com.fthon.subsclife.service.RemindService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        @Operation(
                summary = "회고 생성",
                description = "회고 생성 시 사용되는 API",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(
                                        allOf = { RemindDto.SaveRequest.class },
                                        requiredProperties = { "taskId", "achievementRate", "achieveReason", "failReason", "improvementPlan" }
                                )
                        )
                )
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "생성 성공"),
                @ApiResponse(responseCode = "401", description = "user-id가 없거나 잘못된 값이 전달되었음",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
                @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스(태스크)",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
                @ApiResponse(responseCode = "409", description = "존재하지 않는 리소스(태스크)",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
        })
        public ResponseEntity<HttpStatus> create(
                @RequestHeader("user-id") Long userId,
                @RequestBody @Valid RemindDto.SaveRequest SaveRequest
        ) {
                remindService.create(SaveRequest);

                return ResponseEntity.status(HttpStatus.CREATED).build();
        }


        @GetMapping("/{remindId}")
        @Operation(summary = "회고 상세 조회(단일 조회)", description = "회고 상세 조회 페이지에서 사용되는 API")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "조회 성공",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = RemindDto.DetailResponse.class))),
                @ApiResponse(responseCode = "401", description = "user-id가 없거나 잘못된 값이 전달되었음",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
                @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스(회고)",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
        })
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
