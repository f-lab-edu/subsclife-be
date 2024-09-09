package com.fthon.subsclife.service;

import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.dto.mapper.TaskMapper;
import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fthon.subsclife.fixture.TaskFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Nested
    @DisplayName("태스크 생성 시")
    class Create_Task {

        @Nested
        @DisplayName("유효한 정보가 입력된다면")
        class With_Validated_Info {
            TaskDto.SaveRequest saveRequest;

            @BeforeEach
            void setUp() {
                saveRequest = task_fixture_with_all_info();
            }

            @Test
            @DisplayName("성공적으로 태스크가 등록된다.")
            void register_successfully() {
                //given
                when(taskMapper.toEntity(saveRequest)).thenReturn(task_with_all_info());

                //when
                taskService.saveTask(saveRequest);

                //then
                verify(taskRepository, times(1)).save((any(Task.class)));
            }
        }
    }
}