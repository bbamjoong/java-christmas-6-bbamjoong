package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.exceptions.InvalidDateException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateTest {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("invalidDate")
    @DisplayName("조건에 맞지 않는 날짜 예외 발생")
    void invalidDateTest(String testName, String input) {
        assertThatThrownBy(() -> VisitDate.of(input))
                .isInstanceOf(InvalidDateException.class);
    }

    private static Stream<Arguments> invalidDate() {
        return Stream.of(
                Arguments.of("문자 입력 예외 발생", "hello"),
                Arguments.of("소수 입력 예외 발생", "0.6"),
                Arguments.of("특수 문자 입력 예외 발생", "+_!@"),
                Arguments.of("1 미만 정수 예외 발생", "0"),
                Arguments.of("31 초과 정수 예외 발생", "32")
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ValueSource(strings = {"1", "2", "30", "31"})
    @DisplayName("조건에 맞는 날짜 정상 작동")
    void validDateTest(String input) {
        assertThatCode(() -> VisitDate.of(input))
                .doesNotThrowAnyException();
    }
}