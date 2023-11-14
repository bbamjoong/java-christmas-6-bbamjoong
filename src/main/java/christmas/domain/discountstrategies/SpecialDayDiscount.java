package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.SPECIAL_DISCOUNT;
import static christmas.domain.enums.Constraints.ZERO;

import christmas.domain.enums.SpecialDay;
import java.util.Arrays;

public class SpecialDayDiscount implements DiscountStrategy {
    private final int visitDay;

    private SpecialDayDiscount(int visitDay) {
        this.visitDay = visitDay;
    }

    public static SpecialDayDiscount of(int visitDay) {
        return new SpecialDayDiscount(visitDay);
    }

    @Override
    // 특별 할인
    public int calculate() {
        if (checkSpecialDay()) {
            return SPECIAL_DISCOUNT.getValue();
        }
        return ZERO.getValue();
    }

    // 달력에 별 표시가 있는 특별 할인 날짜 확인
    private boolean checkSpecialDay() {
        return Arrays.stream(SpecialDay.values())
                .anyMatch(specialDay -> specialDay.isSpecialDay(visitDay));
    }
}
