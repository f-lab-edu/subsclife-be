package com.fthon.subsclife.validate;


import com.fthon.subsclife.dto.RemindDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

import static com.fthon.subsclife.fixture.RemindFixture.*;

public class RemindValidateTest {

    private Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("회고 요청 유효성 검사 시")
    class validate_Remind_Action {

        @Nested
        @DisplayName("값이 모두 입력되었다면")
        class With_All_Info {


            RemindDto.RemindRequest remindRequest = remind_fixture_with_all_info();

            @Test
            @DisplayName("유효성 검사를 통과해야 한다.")
            void success_to_validate() {
                Set<ConstraintViolation<RemindDto.RemindRequest>> violations = validator.validate(remindRequest);
                assert (violations.isEmpty());

            }
        }

        @Nested
        @DisplayName("성공률이 없다면")
        class Without_achievementRate {

            RemindDto.RemindRequest remindRequest = remind_fixture_without_achievementRate();

            @Test
            @DisplayName("유효성 검사를 통과하지 못해야 한다.")
            void fail_to_validate() {
                Set<ConstraintViolation<RemindDto.RemindRequest>> violations = validator.validate(remindRequest);
                List<ConstraintViolation<RemindDto.RemindRequest>> violationList = List.copyOf(violations);

                ConstraintViolation<RemindDto.RemindRequest> violation = violationList.iterator().next();
                Assertions.assertEquals("achievementRate", violation.getPropertyPath().toString());
            }
        }

        @Nested
        @DisplayName("성공률에 대한 이유가 없다면.")
        class Without_achieveReason {

            RemindDto.RemindRequest remindRequest = remind_fixture_without_achieveReason();

            @Test
            @DisplayName("유효성 검사를 통과하지 못해야 한다.")
            void fail_to_validate() {
                Set<ConstraintViolation<RemindDto.RemindRequest>> violations = validator.validate(remindRequest);
                List<ConstraintViolation<RemindDto.RemindRequest>> violationList = List.copyOf(violations);

                ConstraintViolation<RemindDto.RemindRequest> violation = violationList.iterator().next();
                Assertions.assertEquals("achieveReason", violation.getPropertyPath().toString());
            }
        }


        @Nested
        @DisplayName("실패 이유가 없다면.")
        class Without_failReason {

            RemindDto.RemindRequest remindRequest = remind_fixture_without_failReason();

            @Test
            @DisplayName("유효성 검사를 통과하지 못해야 한다.")
            void fail_to_validate() {
                Set<ConstraintViolation<RemindDto.RemindRequest>> violations = validator.validate(remindRequest);
                List<ConstraintViolation<RemindDto.RemindRequest>> violationList = List.copyOf(violations);

                ConstraintViolation<RemindDto.RemindRequest> violation = violationList.iterator().next();
                Assertions.assertEquals("failReason", violation.getPropertyPath().toString());
            }
        }

        @Nested
        @DisplayName("개선 계획이 없다면.")
        class Without_improvementPlan {

            RemindDto.RemindRequest remindRequest = remind_fixture_without_improvementPlan();

            @Test
            @DisplayName("유효성 검사를 통과하지 못해야 한다.")
            void fail_to_validate() {
                Set<ConstraintViolation<RemindDto.RemindRequest>> violations = validator.validate(remindRequest);
                List<ConstraintViolation<RemindDto.RemindRequest>> violationList = List.copyOf(violations);

                ConstraintViolation<RemindDto.RemindRequest> violation = violationList.iterator().next();
                Assertions.assertEquals("improvementPlan", violation.getPropertyPath().toString());
            }
        }



    }

}
