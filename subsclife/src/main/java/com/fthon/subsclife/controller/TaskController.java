package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
