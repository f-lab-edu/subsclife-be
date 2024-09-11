package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.exception.ErrorInfo;
import com.fthon.subsclife.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "태스크 관련 동작을 수행합니다.")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "태스크 생성", description = "태스크 생성 요청 시 사용되는 API")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content())
    public ResponseEntity<HttpStatus> createTask(@RequestBody @Valid TaskDto.SaveRequest saveRequestDto) {
        taskService.saveTask(saveRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "태스크 단건 세부 조회", description = "단일 태스크의 세부 정보 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태스크 조회 성공",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.DetailResponse.class))),
            @ApiResponse(responseCode = "401", description = "user-id가 없거나 잘못된 값이 전달되었음",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<TaskDto.DetailResponse> getDetailTaskInfo(
            @RequestHeader("user-id") Long userId,
            @PathVariable("taskId") Long taskId) {

        return new ResponseEntity<>(taskService.getTaskDetail(taskId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "태스크 목록 조회", description = "태스크 목록 조회 시 사용되는 API")
    public ResponseEntity<PagedItem<TaskDto.ListResponse>> searchTaskList(
                @RequestHeader("user-id") Long userId,
                @RequestParam(name = "task_id", required = false) Long taskId,
                @RequestParam(name = "start_date", required = false) LocalDateTime startDate,
                @RequestParam(name = "end_date", required = false) LocalDateTime endDate,
                @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize,
                @RequestParam(name = "start_from", required = false) LocalDateTime startFrom,
                @RequestParam(name = "end_to", required = false) LocalDateTime endTo,
                @RequestParam(name = "keyword", required = false) String keyword) {

        TaskDto.Cursor cursor = getCursor(taskId, startDate, endDate);
        TaskDto.SearchCondition cond = getSearchCondition(pageSize, startFrom, endTo, keyword);

        return new ResponseEntity<>(taskService.getTaskList(cursor, cond), HttpStatus.OK);
    }

    @GetMapping("{taskId}/subscribers")
    @Operation(summary = "태스크 구독자 목록 조회", description = "태스크를 구독한 사용자 목록 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태스크 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.DetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<List<UserDto.Response>> getSubscribers(
            @PathVariable Long taskId) {

        return new ResponseEntity<>(taskService.getSubscriberList(taskId), HttpStatus.OK);
    }

    private static TaskDto.SearchCondition getSearchCondition(Long pageSize, LocalDateTime startFrom, LocalDateTime endTo, String keyword) {
        return TaskDto.SearchCondition.builder()
                .pageSize(pageSize)
                .startFrom(startFrom)
                .endTo(endTo)
                .keyword(keyword)
                .build();
    }

    private static TaskDto.Cursor getCursor(Long taskId, LocalDateTime startDate, LocalDateTime endDate) {
        return TaskDto.Cursor.builder()
                .taskId(taskId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}
