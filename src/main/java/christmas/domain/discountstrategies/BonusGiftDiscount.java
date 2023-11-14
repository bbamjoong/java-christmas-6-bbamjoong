package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.FREE_GIFT_PRICE_THRESHOLD;
import static christmas.domain.enums.Constraints.ZERO;

import christmas.domain.Foods;
import christmas.domain.enums.Menu;

public class BonusGiftDiscount implements DiscountStrategy {
    private final Foods foods;

    private BonusGiftDiscount(Foods foods) {
        this.foods = foods;
    }

    public static BonusGiftDiscount of(Foods foods) {
        return new BonusGiftDiscount(foods);
    }

    @Override
    // 총 구매액이 120_000원 이상일 경우 샴페인 증정
    public int calculate() {
        if (foods.calculateTotalPrice() >= FREE_GIFT_PRICE_THRESHOLD.getValue()) {
            return Menu.CHAMPAGNE.getPrice();
        }
        return ZERO.getValue();
    }
}
