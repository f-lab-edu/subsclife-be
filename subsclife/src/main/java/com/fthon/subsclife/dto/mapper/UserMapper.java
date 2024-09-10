package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.UserDto;
import com.fthon.subsclife.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto.Response toResponseDto(User user) {
        return UserDto.Response.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }
}
