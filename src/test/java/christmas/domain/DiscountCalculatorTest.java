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
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.enums.DiscountType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountCalculatorTest {
    private List<Food> orderFoods;

    @BeforeEach
    void setUp() {
        orderFoods = new ArrayList<>();
        orderFoods.add(Food.of("티본스테이크", "2"));
        orderFoods.add(Food.of("초코케이크", "2"));
    }

    @Test
    @DisplayName("12월 1일 : 크리스마스 할인 + 주말 디저트 메뉴 할인 + 샴페인 증정")
    void christmasEveDiscountsTest() {
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, 1);

        Map<DiscountType, Integer> result = discountCalculator.calculateDiscount();
        Map<DiscountType, Integer> expected = expected1stDayDiscount();

        assertThat(result).isEqualTo(expected);
    }

    private Map<DiscountType, Integer> expected1stDayDiscount() {
        return Map.of(
                // 크리스마스 할인
                CHRISTMAS, BASE_DISCOUNT.getValue(),
                // 주간 할인
                WEEKDAY, ZERO.getValue(),
                // 주말 할인
                WEEKEND, WEEK_DISCOUNT.getValue() * 2,
                // 특별 할인
                SPECIAL, ZERO.getValue(),
                // 증정품 제공
                BONUS_GIFT, CHAMPAGNE.getPrice()
        );
    }

    @Test
    @DisplayName("12월 25일 : 크리스마스 할인 + 주간 메인 메뉴 할인 + 특별 할인 + 샴페인 증정")
    void christmasDiscountsTest() {
        Foods orderedFoods = Foods.of(orderFoods);
        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods,
                CHRISTMAS_EVENT_END_DATE.getValue());

        Map<DiscountType, Integer> result = discountCalculator.calculateDiscount();
        Map<DiscountType, Integer> expected = expectedChristmasDiscount();

        assertThat(result).isEqualTo(expected);
    }

    private Map<DiscountType, Integer> expectedChristmasDiscount() {
        return Map.of(
                // 크리스마스 할인
                CHRISTMAS, BASE_DISCOUNT.getValue() + DISCOUNT_PER_DAY.getValue()
                        * (CHRISTMAS_EVENT_END_DATE.getValue() - CHRISTMAS_EVENT_START_DATE.getValue()),
                // 주간 할인
                WEEKDAY, WEEK_DISCOUNT.getValue() * 2,
                // 주말 할인
                WEEKEND, ZERO.getValue(),
                // 특별 할인
                SPECIAL, SPECIAL_DISCOUNT.getValue(),
                // 증정품 제공
                BONUS_GIFT, CHAMPAGNE.getPrice()
        );
    }
}