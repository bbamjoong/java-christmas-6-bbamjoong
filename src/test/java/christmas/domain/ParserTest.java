package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.exceptions.InvalidOrderException;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParserTest {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("invalidSizeInput")
    @DisplayName("입력 양식을 지키지 않은 경우 예외 발생")
    void invalidSizeInputTest(String testName, String input) {
        assertThatThrownBy(() -> Parser.parseInput(input))
                .isInstanceOf(InvalidOrderException.class);
    }

    private static Stream<Arguments> invalidSizeInput() {
        return Stream.of(
                Arguments.of("쉼표 마지막 입력 예외 발생", "a-1, b-2,"),
                Arguments.of("개수 입력 X 예외 발생1", "a-1, b-2, c"),
                Arguments.of("개수 입력 X 예외 발생2", "a, b-2"),
                Arguments.of("개수 입력 X 예외 발생3", "a, b, c"),
                Arguments.of("메뉴 입력 X 예외 발생1", "1, 2, 3"),
                Arguments.of("메뉴 입력 X 예외 발생2", "a-1, 2, 3"),
                Arguments.of("Hyphen 여러개 입력 시 예외 발생", "a-b-1, 2, 3, 4")
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("duplicateKeyInput")
    @DisplayName("중복된 메뉴 입력 시 예외 발생")
    void duplicateMenuInputTest(String testName, String input) {
        assertThatThrownBy(() -> Parser.parseInput(input))
                .isInstanceOf(InvalidOrderException.class);
    }

    private static Stream<Arguments> duplicateKeyInput() {
        return Stream.of(
                Arguments.of("중복된 키 값 예외 발생1", "a-1, a-1"),
                Arguments.of("중복된 키 값 예외 발생2", "a-1, a-1, b-3"),
                Arguments.of("중복된 키 값 예외 발생3", "a-1, b-1, c-1, a-1")
        );
    }

    @Test
    @DisplayName("정상 입력 시 예외 발생 X")
    void validInputTest() {
        String input = "a-1, b-2, c-3";
        assertThatCode(() -> Parser.parseInput(input))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("정상 입력 시 올바른 Map 반환")
    void validInputCreatesValidMapTest() {
        //given
        String input = "a-1, b-2, c-3";
        //when
        Map<String, String> result = Parser.parseInput(input);
        //then
        Map<String, String> expected = Map.of("a", "1", "b", "2", "c", "3");
        assertThat(result).isEqualTo(expected);
    }
}