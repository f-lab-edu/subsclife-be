package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.exception.ErrorInfo;
import com.fthon.subsclife.service.LoginService;
import com.fthon.subsclife.service.SubscriptionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final LoginService loginService;

    private final SubscriptionFacade subscriptionFacade;

    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "로그인 - 임시", description = "header를 통해 전달된 user-id에 해당하는 사용자 정보를 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.Response.class))),
            @ApiResponse(responseCode = "401", description = "user-id가 없거나 잘못된 값이 전달되었음",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<UserDto.Response> login() {
        User user = loginService.getLoginUser();

        return new ResponseEntity<>(userMapper.toResponseDto(user), HttpStatus.OK);
    }


    @PostMapping("/subscribe")
    @Operation(summary = "구독", description = "특정 태스크에 대한 구독을 진행합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구독 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
            @ApiResponse(responseCode = "409", description = "구독 실패(개수 제한, 중복 구독 등 비즈니스에 의한 실패)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<HttpStatus> subscribe(
            @RequestHeader("user-id") Long userId,
            @RequestParam("task_id") Long taskId) {

        subscriptionFacade.subscribe(userId, taskId);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/unsubscribe")
    @Operation(summary = "구독 해제", description = "특정 태스크에 대한 구독을 해제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구독 해제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<HttpStatus> unsubscribe(
            @RequestHeader("user-id") Long userId,
            @RequestParam("task_id") Long taskId) {

        subscriptionFacade.unsubscribe(taskId, userId);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{userId}/subscribes")
    @Operation(summary = "구독 리스트", description = "특정 사용자가 구독한 태스크 목록을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = UserDto.SubscribedTaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<List<UserDto.SubscribedTaskResponse>> getSubscribedTasks(
            @RequestHeader("user-id") Long loginUserId, // 로그인 시 사용되는 정보
            @PathVariable("userId") Long userId
    ) {
        return new ResponseEntity<>(subscriptionFacade.getSubscribedTasks(userId), HttpStatus.OK);
    }

}
