package com.fthon.subsclife.validate;


import com.fthon.subsclife.dto.TaskDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static com.fthon.subsclife.fixture.TaskFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * DTO Class의 validation 기능을 테스트합니다.
 */
public class TaskValidateTest {

    private Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("태스크 요청 유효성 검사 시")
    class Validate_Task_Action {

        @Nested
        @DisplayName("값이 모두 입력되었다면")
        class With_All_Info {

            TaskDto.SaveRequest saveRequest = task_fixture_with_all_info();

            @Test
            @DisplayName("검증에 성공한다.")
            void success_to_validate() {
                Set<ConstraintViolation<TaskDto.SaveRequest>> violations = validator.validate(saveRequest);
                assertThat(violations).isEmpty();
            }
        }

        @Nested
        @DisplayName("태스크 설명이 비어있다면")
        class Without_Detail_Info {

            TaskDto.SaveRequest saveRequest= task_fixture_without_detail_info();

            @Test
            @DisplayName("검증에 성공한다.")
            void success_to_validate() {
                Set<ConstraintViolation<TaskDto.SaveRequest>> violations = validator.validate(saveRequest);
                assertThat(violations).isEmpty();
            }
        }

        @Nested
        @DisplayName("태스크 제목이 비어있다면")
        class Without_Task_Title {

            TaskDto.SaveRequest saveRequest = task_fixture_without_title();

            @Test
            @DisplayName("검증에 실패한다")
            void fail_to_validate() {
                Set<ConstraintViolation<TaskDto.SaveRequest>> violations = validator.validate(saveRequest);
                ConstraintViolation<TaskDto.SaveRequest> violation = violations.iterator().next();
                Assertions.assertEquals("title", violation.getPropertyPath().toString());
            }
        }
    }
}
