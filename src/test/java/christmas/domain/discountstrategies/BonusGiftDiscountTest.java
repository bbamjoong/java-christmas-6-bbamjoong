package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.domain.Food;
import christmas.domain.Foods;
import christmas.domain.enums.Menu;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BonusGiftDiscountTest {
    @Test
    @DisplayName("120,000 이상 구매 시 샴페인 증정")
    void bonusGiftDiscountTest() {
        List<Food> bonusGiftFoods = List.of(
                Food.of("티본스테이크", "2"),
                Food.of("초코케이크", "2"));
        Foods orderedFoods = Foods.of(bonusGiftFoods);

        int resultDiscount = calculateDiscount(orderedFoods);
        int expectedDiscount = Menu.CHAMPAGNE.getPrice();

        assertThat(resultDiscount).isEqualTo(expectedDiscount);
    }

    @Test
    @DisplayName("120,000 미만 구매 시 샴페인 증정 없음")
    void noBonusGiftDiscountTest() {
        List<Food> noBonusGiftFoods = List.of(Food.of("초코케이크", "2"));
        Foods orderedFoods = Foods.of(noBonusGiftFoods);

        int resultDiscount = calculateDiscount(orderedFoods);
        int expectedDiscount = ZERO.getValue();
        
        assertThat(resultDiscount).isEqualTo(expectedDiscount);
    }

    private int calculateDiscount(Foods orderedFoods) {
        return BonusGiftDiscount.of(orderedFoods).calculate();
    }
}