package com.fthon.subsclife.controller;


import com.fthon.subsclife.dto.UserResponseDto;
import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestHeader("user_id") Long userId) {
        User user = userService.findUserById(userId);

        return new ResponseEntity<>(userMapper.toResponseDto(user), HttpStatus.OK);
    }

}
