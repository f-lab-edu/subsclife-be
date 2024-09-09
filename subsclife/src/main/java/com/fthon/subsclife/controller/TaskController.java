package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.service.TaskService;
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
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<HttpStatus> createTask(@RequestBody @Valid TaskDto.SaveRequest saveRequestDto) {
        taskService.saveTask(saveRequestDto);

        return ResponseEntity.ok().build();
    }
}
