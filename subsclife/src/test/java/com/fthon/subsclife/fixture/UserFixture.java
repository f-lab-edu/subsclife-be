package com.fthon.subsclife.fixture;

import com.fthon.subsclife.dto.UserResponseDto;
import com.fthon.subsclife.entity.User;

public class UserFixture {

    public static final String USER_NAME = "test user";
    public static final String USER_NICKNAME = "test nickname";

    public static User user_fixture() {
        return User.builder()
                .name(USER_NAME)
                .nickname(USER_NICKNAME)
                .build();
    }

    public static UserResponseDto user_response_dto_fixture() {
        return UserResponseDto.builder()
                .name(USER_NAME)
                .nickname(USER_NICKNAME)
                .build();
    }
}
