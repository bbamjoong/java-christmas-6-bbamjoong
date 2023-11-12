package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.exceptions.InvalidOrderException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class FoodTest {
    @ParameterizedTest(name = "{index}: {0}")
    @ValueSource(strings = {"치킨", "피자", "햄버거", "맥주"})
    @DisplayName("메뉴판에 존재 하지 않는 음식 입력 시 예외 발생")
    void invalidMenuInputTest(String name) {
        assertThatThrownBy(() -> Food.of(name, "1"))
                .isInstanceOf(InvalidOrderException.class);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드",
            "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타",
            "초코케이크", "아이스크림",
            "제로콜라", "레드와인", "샴페인"})
    @DisplayName("메뉴판에 존재하는 음식일 경우 정상 작동")
    void validMenuInputTest(String name) {
        assertThatCode(() -> Food.of(name, "1"))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("invalidIntegerInput")
    @DisplayName("1이상의 정수가 아닐 경우 예외 발생")
    void invalidCountInputTest(String testName, String count) {
        assertThatThrownBy(() -> Food.of("양송이수프", count))
                .isInstanceOf(InvalidOrderException.class);
    }

    private static Stream<Arguments> invalidIntegerInput() {
        return Stream.of(
                Arguments.of("null 예외 발생", null),
                Arguments.of("빈 값 예외 발생", ""),
                Arguments.of("공백 예외 발생", " "),
                Arguments.of("문자 + 공백 예외 발생", "a 1"),
                Arguments.of("특수 문자 예외 발생", "1+2"),
                Arguments.of("소수 예외 발생", "0.7"),
                Arguments.of("0 예외 발생", "0"),
                Arguments.of("음의 정수 예외 발생", "-10")
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @DisplayName("1이상의 정수일 경우 정상 작동")
    void validCountInputTest(String count) {
        assertThatCode(() -> Food.of("양송이수프", count))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("양송이수프는 6천원")
    void checkPriceTest() {
        Food soup = Food.of("양송이수프", "1");
        int result = soup.price();
        int expectedPrice = Menu.MUSHROOM_SOUP.getPrice();

        assertThat(result).isEqualTo(expectedPrice);
    }
}