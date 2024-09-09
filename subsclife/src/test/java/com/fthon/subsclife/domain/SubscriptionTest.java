package com.fthon.subsclife.domain;

import com.fthon.subsclife.entity.Period;
import com.fthon.subsclife.entity.Subscribe;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.entity.User;
import com.fthon.subsclife.exception.TaskSubscriptionClosedException;
import com.fthon.subsclife.exception.UserSubscriptionOverflowException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fthon.subsclife.common.Constants.TASK_SUBSCRIBER_LIMIT;
import static com.fthon.subsclife.common.Constants.USER_SUBSCRIBE_LIMIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionTest {

    @InjectMocks
    private User user;

    @Mock
    private Task task;

    @Mock
    private Subscribe subscribe;


    @Nested
    @DisplayName("태스크 구독을 시도할 때")
    class Request_Subscribe_Task {

        @Nested
        @DisplayName("태스크의 구독자 수가 제한보다 많다면")
        class Subscriber_Over_Limit {

            @BeforeEach
            void setUp() {
                //given
                when(task.getSubscriberCount()).thenReturn(TASK_SUBSCRIBER_LIMIT + 1); // 제한을 초과하는 값 설정
            }

            @Test
            @DisplayName("예외가 발생한다.")
            void throws_task_over_exception() {
                //when & then
                assertThrows(TaskSubscriptionClosedException.class, () -> user.subscribe(task));
            }
        }

        @Nested
        @DisplayName("사용자의 구독 수가 제한보다 많다면")
        class Subscribe_Over_Limit {

            @BeforeEach
            void setUp() {
                //given
                when(task.getPeriod()).thenReturn(
                        Period.builder()
                                .startDate(LocalDateTime.now())
                                .endDate(LocalDateTime.now().plusDays(1))
                                .build()
                );

                List<Subscribe> subscribes = new ArrayList<>();
                for (int i = 0; i < USER_SUBSCRIBE_LIMIT + 1; i++) {
                    Subscribe validSubscribe = Subscribe.builder()
                            .user(user)
                            .task(task)
                            .build();
                    subscribes.add(validSubscribe);
                }
                user.getSubscribes().addAll(subscribes); // 구독 리스트에 생성된 구독 추가
            }

            @Test
            @DisplayName("예외가 발생한다.")
            void throws_subscribe_over_exception() {
                //when & then
                assertThrows(UserSubscriptionOverflowException.class, () -> user.subscribe(task));
            }
        }
    }
}
