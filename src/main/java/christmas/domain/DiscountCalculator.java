package christmas.domain;

import static christmas.domain.enums.DiscountType.BONUS_GIFT;
import static christmas.domain.enums.DiscountType.CHRISTMAS;
import static christmas.domain.enums.DiscountType.SPECIAL;
import static christmas.domain.enums.DiscountType.WEEKDAY;
import static christmas.domain.enums.DiscountType.WEEKEND;

import christmas.domain.discountstrategies.BonusGiftDiscount;
import christmas.domain.discountstrategies.ChristmasEventDiscount;
import christmas.domain.discountstrategies.DiscountStrategy;
import christmas.domain.discountstrategies.SpecialDayDiscount;
import christmas.domain.discountstrategies.WeekdayDiscount;
import christmas.domain.discountstrategies.WeekendDiscount;
import christmas.domain.enums.DiscountType;
import java.util.Map;

public class DiscountCalculator {
    private final int visitDay;
    private final Foods foods;

    private DiscountCalculator(Foods foods, int visitDay) {
        this.foods = foods;
        this.visitDay = visitDay;
    }

    public static DiscountCalculator of(Foods foods, int visitDay) {
        return new DiscountCalculator(foods, visitDay);
    }

    // 모든 항목에 대해 할인 계산
    public Map<DiscountType, Integer> calculateDiscount() {
        return Map.of(
                CHRISTMAS, calculateDiscount(ChristmasEventDiscount.of(visitDay)),
                WEEKDAY, calculateDiscount(WeekdayDiscount.of(foods, visitDay)),
                WEEKEND, calculateDiscount(WeekendDiscount.of(foods, visitDay)),
                SPECIAL, calculateDiscount(SpecialDayDiscount.of(visitDay)),
                BONUS_GIFT, calculateDiscount(BonusGiftDiscount.of(foods))
        );
    }

    // 할인 계산
    private int calculateDiscount(DiscountStrategy discountStrategy) {
        return discountStrategy.calculate();
    }
}