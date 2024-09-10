package com.fthon.subsclife.fixture;

import com.fthon.subsclife.dto.UserDto;
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

    public static UserDto.Response user_response_dto_fixture() {
        return UserDto.Response.builder()
                .name(USER_NAME)
                .nickname(USER_NICKNAME)
                .build();
    }
}
