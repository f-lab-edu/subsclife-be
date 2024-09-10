package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.service.LoginService;
import com.fthon.subsclife.service.SubscriptionFacade;
import com.fthon.subsclife.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LoginService loginService;

    private final SubscriptionFacade subscriptionFacade;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login() {
        User user = loginService.getLoginUser();

        return new ResponseEntity<>(userMapper.toResponseDto(user), HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<HttpStatus> subscribe(
            @RequestHeader("user-id") Long userId,
            @RequestParam("task_id") Long taskId) {

        subscriptionFacade.subscribe(userId, taskId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<HttpStatus> unsubscribe(
            @RequestHeader("user-id") Long userId,
            @RequestParam("task_id") Long taskId) {

        subscriptionFacade.unsubscribe(taskId, userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/subscribes")
    public ResponseEntity<List<UserDto.SubscribedTaskResponse>> getSubscribedTasks(
            @RequestHeader("user-id") Long loginUserId, // 로그인 시 사용되는 정보
            @PathVariable("userId") Long userId
    ) {
        return new ResponseEntity<>(subscriptionFacade.getSubscribedTasks(userId), HttpStatus.OK);
    }

}
