package com.fthon.subsclife.dto.mapper;

import com.fthon.subsclife.dto.UserResponseDto;
import com.fthon.subsclife.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }
}
