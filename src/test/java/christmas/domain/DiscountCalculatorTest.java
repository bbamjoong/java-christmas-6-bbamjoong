package christmas.domain;

import static christmas.domain.Constraints.BASE_DISCOUNT;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.Constraints.ZERO;
import static christmas.domain.DiscountType.CHRISTMAS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountCalculatorTest {
    List<Food> orderFoods;
    DiscountCalculator discountCalculator;

    @BeforeEach
    void setUp() {
        orderFoods = new ArrayList<>();
        orderFoods.add(Food.of("티본스테이크", "2"));
        orderFoods.add(Food.of("초코케이크", "2"));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("christmasDiscountParameter")
    @DisplayName("날짜에 따른 크리스마스 할인 테스트")
    void christmasDiscountTest(String testName, int visitDay, int expectedDiscount) {
        discountCalculator = new DiscountCalculator(Foods.of(orderFoods), visitDay);

        Map<String, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(CHRISTMAS.getLabel());

        assertThat(result).isEqualTo(expectedDiscount);
    }

    static Stream<Arguments> christmasDiscountParameter() {
        return Stream.of(
                Arguments.of("1일은 1000원 할인", CHRISTMAS_EVENT_START_DATE.getValue(), BASE_DISCOUNT.getValue()),
                Arguments.of("25일은 3400원 할인", CHRISTMAS_EVENT_END_DATE.getValue(), BASE_DISCOUNT.getValue()
                        + DISCOUNT_PER_DAY.getValue() * (CHRISTMAS_EVENT_END_DATE.getValue()
                        - CHRISTMAS_EVENT_START_DATE.getValue())),
                Arguments.of("26일은 0원 할인", 26, ZERO.getValue())
        );
    }
}