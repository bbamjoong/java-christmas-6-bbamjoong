package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.SPECIAL_DISCOUNT;
import static christmas.domain.enums.Constraints.ZERO;
import static christmas.domain.enums.SpecialDay.NOVEMBER_10;
import static christmas.domain.enums.SpecialDay.NOVEMBER_17;
import static christmas.domain.enums.SpecialDay.NOVEMBER_24;
import static christmas.domain.enums.SpecialDay.NOVEMBER_25;
import static christmas.domain.enums.SpecialDay.NOVEMBER_3;
import static christmas.domain.enums.SpecialDay.NOVEMBER_31;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SpecialDayDiscountTest {
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("specialDiscountParameter")
    @DisplayName("특별 할인 테스트")
    void specialDiscountTest(String testName, int visitDay, int expectedDiscount) {
        int resultDiscount = calculateDiscount(visitDay);
        assertThat(resultDiscount).isEqualTo(expectedDiscount);
    }

    static Stream<Arguments> specialDiscountParameter() {
        return Stream.of(
                Arguments.of("3일 할인", NOVEMBER_3.getValue(), SPECIAL_DISCOUNT.getValue()),
                Arguments.of("10일 할인", NOVEMBER_10.getValue(), SPECIAL_DISCOUNT.getValue()),
                Arguments.of("17일 할인", NOVEMBER_17.getValue(), SPECIAL_DISCOUNT.getValue()),
                Arguments.of("24일 할인", NOVEMBER_24.getValue(), SPECIAL_DISCOUNT.getValue()),
                Arguments.of("25일 할인", NOVEMBER_25.getValue(), SPECIAL_DISCOUNT.getValue()),
                Arguments.of("31일 할인", NOVEMBER_31.getValue(), SPECIAL_DISCOUNT.getValue()),

                Arguments.of("1일 할인 X", 1, ZERO.getValue()),
                Arguments.of("8일 할인 X", 8, ZERO.getValue()),
                Arguments.of("26일 할인 X", 26, ZERO.getValue())
        );
    }

    private int calculateDiscount(int visitDay) {
        return SpecialDayDiscount.of(visitDay).calculate();
    }
}