package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.BASE_DISCOUNT;
import static christmas.domain.enums.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.enums.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.enums.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.enums.Constraints.ZERO;

public class ChristmasEventDiscount implements DiscountStrategy {
    private final int visitDay;

    private ChristmasEventDiscount(int visitDay) {
        this.visitDay = visitDay;
    }

    public static ChristmasEventDiscount of(int visitDay) {
        return new ChristmasEventDiscount(visitDay);
    }

    @Override
    // 크리스마스 이벤트 할인
    public int calculate() {
        if (isDuringChristmasEvent()) {
            return calculateChristmasEventDiscount();
        }
        return ZERO.getValue();
    }

    // 크리스마스 이벤트 해당 날짜 확인
    private boolean isDuringChristmasEvent() {
        return visitDay >= CHRISTMAS_EVENT_START_DATE.getValue()
                && visitDay <= CHRISTMAS_EVENT_END_DATE.getValue();
    }

    // 크리스마스 할인 계산
    private int calculateChristmasEventDiscount() {
        int daysFromStart = visitDay - CHRISTMAS_EVENT_START_DATE.getValue();
        return BASE_DISCOUNT.getValue() + daysFromStart * DISCOUNT_PER_DAY.getValue();
    }
}
