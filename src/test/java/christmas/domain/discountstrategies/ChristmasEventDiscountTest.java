package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.BASE_DISCOUNT;
import static christmas.domain.enums.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.enums.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.enums.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.enums.Constraints.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasEventDiscountTest {
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("christmasDiscountParameter")
    @DisplayName("날짜에 따른 크리스마스 할인 테스트")
    void christmasDiscountTest(String testName, int visitDay, int expectedDiscount) {
        int resultDiscount = calculateDiscount(visitDay);
        assertThat(resultDiscount).isEqualTo(expectedDiscount);
    }

    static Stream<Arguments> christmasDiscountParameter() {
        return Stream.of(
                Arguments.of("1일은 1000원 할인", CHRISTMAS_EVENT_START_DATE.getValue(), BASE_DISCOUNT.getValue()),
                Arguments.of("25일은 3400원 할인", CHRISTMAS_EVENT_END_DATE.getValue(),
                        BASE_DISCOUNT.getValue() + DISCOUNT_PER_DAY.getValue()
                                * (CHRISTMAS_EVENT_END_DATE.getValue() - CHRISTMAS_EVENT_START_DATE.getValue())
                ),
                Arguments.of("26일은 0원 할인", 26, ZERO.getValue())
        );
    }

    private int calculateDiscount(int visitDay) {
        return ChristmasEventDiscount.of(visitDay).calculate();
    }
}