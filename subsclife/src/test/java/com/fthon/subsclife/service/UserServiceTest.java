package com.fthon.subsclife.service;

import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("찾으려는 사용자가 존재한다면")
    class Find_User_Exists {

        @Test
        @DisplayName("성공적으로 반환하다")
        void return_successfully() {
            //given
            Long userId = 1L;
            User mockUser = mock(User.class);
            when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
            when(mockUser.getId()).thenReturn(userId);

            //when
            User user = userService.findUserById(userId);

            //then
            Assertions.assertEquals(userId, user.getId());
        }
    }

    @Nested
    @DisplayName("찾으려는 사용자가 존재하지 않는다면")
    class Find_User_Not_Exists {

        @Test
        @DisplayName("예외가 발생한다")
        void throws_exception() {
            //given
            Long userId = 1L;
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            //when & then
            assertThrows(NoSuchElementException.class, () -> userService.findUserById(userId));
        }
    }
}