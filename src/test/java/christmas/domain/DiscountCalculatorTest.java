package christmas.domain;

import static christmas.domain.enums.Constraints.BASE_DISCOUNT;
import static christmas.domain.enums.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.enums.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.enums.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.enums.Constraints.SPECIAL_DISCOUNT;
import static christmas.domain.enums.Constraints.WEEK_DISCOUNT;
import static christmas.domain.enums.Constraints.ZERO;
import static christmas.domain.enums.DiscountType.BONUS_GIFT;
import static christmas.domain.enums.DiscountType.CHRISTMAS;
import static christmas.domain.enums.DiscountType.SPECIAL;
import static christmas.domain.enums.DiscountType.WEEKDAY;
import static christmas.domain.enums.DiscountType.WEEKEND;
import static christmas.domain.enums.Menu.CHAMPAGNE;
import static christmas.domain.enums.SpecialDay.NOVEMBER_10;
import static christmas.domain.enums.SpecialDay.NOVEMBER_17;
import static christmas.domain.enums.SpecialDay.NOVEMBER_24;
import static christmas.domain.enums.SpecialDay.NOVEMBER_25;
import static christmas.domain.enums.SpecialDay.NOVEMBER_3;
import static christmas.domain.enums.SpecialDay.NOVEMBER_31;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.domain.enums.DiscountType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountCalculatorTest {
    List<Food> orderFoods;

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
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, visitDay);

        Map<DiscountType, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(CHRISTMAS);

        assertThat(result).isEqualTo(expectedDiscount);
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

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("weekdayDiscountParameter")
    @DisplayName("주간 메인 메뉴 할인 테스트")
    void weekdayDiscountTest(String testName, int visitDay, int expectedDiscount) {
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, visitDay);

        Map<DiscountType, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(WEEKDAY);

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
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, visitDay);

        Map<DiscountType, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(WEEKEND);

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
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, visitDay);

        Map<DiscountType, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(SPECIAL);

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

    @Test
    @DisplayName("120,000 이상 구매 샴페인 증정")
    void bonusGiftDiscountTest() {
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, 1);

        Map<DiscountType, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(BONUS_GIFT);

        assertThat(result).isEqualTo(CHAMPAGNE.getPrice());
    }

    @Test
    @DisplayName("120,000 미만 구매 샴페인 증정X")
    void noBonusGiftDiscountTest() {
        List<Food> noBonusGiftFoods = new ArrayList<>();
        noBonusGiftFoods.add(Food.of("초코케이크", "2"));
        Foods orderedFoods = Foods.of(noBonusGiftFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, 1);

        Map<DiscountType, Integer> discounts = discountCalculator.calculateDiscount();
        Integer result = discounts.get(BONUS_GIFT);

        assertThat(result).isEqualTo(ZERO.getValue());
    }

    @Test
    @DisplayName("12월 25일 : 크리스마스 할인 + 주간 메인 메뉴 할인 + 특별 할인 + 샴페인 증정")
    void testChristmasTest() {
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods,
                CHRISTMAS_EVENT_END_DATE.getValue());

        Map<DiscountType, Integer> result = discountCalculator.calculateDiscount();
        Map<DiscountType, Integer> expected = Map.of(
                CHRISTMAS, BASE_DISCOUNT.getValue() + DISCOUNT_PER_DAY.getValue()
                        * (CHRISTMAS_EVENT_END_DATE.getValue() - CHRISTMAS_EVENT_START_DATE.getValue()),
                WEEKDAY, WEEK_DISCOUNT.getValue() * 2,
                WEEKEND, ZERO.getValue(),
                SPECIAL, SPECIAL_DISCOUNT.getValue(),
                BONUS_GIFT, CHAMPAGNE.getPrice()
        );

        assertThat(result).isEqualTo(expected);
    }
}