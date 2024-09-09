package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.UserResponseDto;
import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.service.SubscriptionFacade;
import com.fthon.subsclife.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final SubscriptionFacade subscriptionFacade;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestHeader("user-id") Long userId) {
        User user = userService.findUserById(userId);

        return new ResponseEntity<>(userMapper.toResponseDto(user), HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<HttpStatus> subscribe(
            @RequestHeader("user-id") Long userId,
            @RequestParam("task_id") Long taskId) {

        subscriptionFacade.subscribe(taskId, userId);

        return ResponseEntity.ok().build();
    }

}
