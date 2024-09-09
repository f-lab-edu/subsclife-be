package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.RemindDto;
import com.fthon.subsclife.dto.mapper.RemindMapper;
import com.fthon.subsclife.entity.Remind;
import com.fthon.subsclife.repository.RemindRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fthon.subsclife.fixture.RemindFixture.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemindServiceTest {

    @InjectMocks
    private RemindService remindService;

    @Mock
    private RemindRepository remindRepository;

    @Mock
    private RemindMapper remindMapper;

    @Nested
    @DisplayName("회고 생성시")
    class Create_Remind {

        @Nested
        @DisplayName("유효한 정보가 입력된다면")
        class With_Validated_Info {
            RemindDto.RemindRequest remindRequest;

            @BeforeEach
            void setUp() {
                remindRequest = remind_fixture_with_all_info();
            }

            @Test
            @DisplayName("정상적으로 회고가 생성되어야 한다.")
            void register_successfully() {
                // given
                when(remindMapper.toEntity(remindRequest)).thenReturn(remind_with_all_info());

                // when
                remindService.create(remindRequest);

                // then
                verify(remindRepository, times(1)).save(any(Remind.class));

            }


        }




    }

}