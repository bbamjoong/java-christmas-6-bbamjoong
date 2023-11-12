package christmas.domain;

import static christmas.domain.Constraints.BASE_DISCOUNT;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.Constraints.SPECIAL_DISCOUNT;
import static christmas.domain.Constraints.WEEK_DISCOUNT;
import static christmas.domain.Constraints.ZERO;
import static christmas.domain.DiscountType.CHRISTMAS;
import static christmas.domain.DiscountType.SPECIAL;
import static christmas.domain.DiscountType.WEEKDAY;
import static christmas.domain.DiscountType.WEEKEND;
import static christmas.domain.SpecialDay.NOVEMBER_10;
import static christmas.domain.SpecialDay.NOVEMBER_17;
import static christmas.domain.SpecialDay.NOVEMBER_24;
import static christmas.domain.SpecialDay.NOVEMBER_25;
import static christmas.domain.SpecialDay.NOVEMBER_3;
import static christmas.domain.SpecialDay.NOVEMBER_31;
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

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("weekdayDiscountParameter")
    @DisplayName("주간 메인 메뉴 할인 테스트")
    void weekdayDiscountTest(String testName, int visitDay, int expectedDiscount) {
        discountCalculator = new DiscountCalculator(Foods.of(orderFoods), visitDay);

        Map<String, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(WEEKDAY.getLabel());

        assertThat(result).isEqualTo(expectedDiscount);
    }

    static Stream<Arguments> weekdayDiscountParameter() {
        return Stream.of(
                Arguments.of("주간 메인 메뉴 개당 2023원 할인", 7, WEEK_DISCOUNT.getValue() * 2),
                Arguments.of("주말 메인 메뉴 할인 X", 8, ZERO.getValue())
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("weekendDiscountParameter")
    @DisplayName("주말 디저트 메뉴 할인 테스트")
    void weekendDiscountTest(String testName, int visitDay, int expectedDiscount) {
        discountCalculator = new DiscountCalculator(Foods.of(orderFoods), visitDay);

        Map<String, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(WEEKEND.getLabel());

        assertThat(result).isEqualTo(expectedDiscount);
    }

    static Stream<Arguments> weekendDiscountParameter() {
        return Stream.of(
                Arguments.of("주말 디저트 메뉴 개당 2023원 할인", 9, WEEK_DISCOUNT.getValue() * 2),
                Arguments.of("주간 디저트 메뉴 할인 X", 10, ZERO.getValue())
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("specialDiscountParameter")
    @DisplayName("특별 할인 테스트")
    void specialDiscountTest(String testName, int visitDay, int expectedDiscount) {
        discountCalculator = new DiscountCalculator(Foods.of(orderFoods), visitDay);

        Map<String, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(SPECIAL.getLabel());

        assertThat(result).isEqualTo(expectedDiscount);
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
}