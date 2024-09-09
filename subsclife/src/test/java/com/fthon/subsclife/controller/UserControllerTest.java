package com.fthon.subsclife.controller;

import com.fthon.subsclife.dto.mapper.UserMapper;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.exception.advice.GlobalExceptionAdvice;
import com.fthon.subsclife.service.SubscriptionFacade;
import com.fthon.subsclife.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static com.fthon.subsclife.fixture.UserFixture.*;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(GlobalExceptionAdvice.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SubscriptionFacade subscriptionFacade;

    @MockBean
    private UserMapper userMapper;

    @Nested
    @DisplayName("로그인 시")
    class Login_Test {

        @Nested
        @DisplayName("Header에 'user_id'가 존재하는 경우")
        class User_Id_Exists {

            @Test
            @DisplayName("사용자가 존재한다면 사용자 정보와 '200' 코드를 리턴한다")
            void login_successfully() throws Exception {
                //given
                Long userId = 1L;
                User user = user_fixture();
                given(userService.findUserById(userId)).willReturn(user);
                given(userMapper.toResponseDto(user)).willReturn(user_response_dto_fixture());

                //when & then
                mockMvc.perform(post("/api/v1/users/login")
                                .header("user-id", userId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(USER_NAME))
                        .andExpect(jsonPath("$.nickname").value(USER_NICKNAME));
            }

            @Test
            @DisplayName("사용자가 존재하지 않으면 '404' 코드를 리턴한다")
            void login_failed() throws Exception {
                //given
                Long userId = 2L;
                given(userService.findUserById(userId)).willThrow(NoSuchElementException.class);

                //when & then
                mockMvc.perform(post("/api/v1/users/login")
                            .header("user-id", userId)
                            .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }
}