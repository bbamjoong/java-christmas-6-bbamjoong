package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.WEEK_DISCOUNT;
import static christmas.domain.enums.Constraints.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Food;
import christmas.domain.Foods;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekdayDiscountTest {
    private List<Food> bonusGiftFoods;

    @BeforeEach
    void setUp() {
        bonusGiftFoods = List.of(
                Food.of("티본스테이크", "2"),
                Food.of("초코케이크", "2")
        );
    }

    @Test
    @DisplayName("주간 메인 메뉴 할인 테스트")
    void weekdayDiscountTest() {
        Foods orderedFoods = Foods.of(bonusGiftFoods);

        int resultDiscount = calculateDiscount(orderedFoods, 7);
        int expectedDiscount = WEEK_DISCOUNT.getValue() * 2;

        assertThat(resultDiscount).isEqualTo(expectedDiscount);
    }

    @Test
    @DisplayName("주말 메인 메뉴 할인 X")
    void noWeekdayDiscountTest() {
        Foods orderedFoods = Foods.of(bonusGiftFoods);

        int resultDiscount = calculateDiscount(orderedFoods, 8);
        int expectedDiscount = ZERO.getValue();

        assertThat(resultDiscount).isEqualTo(expectedDiscount);
    }

    private int calculateDiscount(Foods orderedFoods, int visitDay) {
        return WeekdayDiscount.of(orderedFoods, visitDay).calculate();
    }
}