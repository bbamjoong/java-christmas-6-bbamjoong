package christmas.domain;

import static christmas.domain.Constraints.BASE_DISCOUNT;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.Constraints.ZERO;
import static christmas.domain.DiscountType.CHRISTMAS;

import java.util.Map;

public class DiscountCalculator {
    private final int visitDay;
    private final Foods foods;

    public DiscountCalculator(Foods foods, int visitDay) {
        this.foods = foods;
        this.visitDay = visitDay;
    }

    public Map<String, Integer> calculateDiscount() {
        return Map.of(
                CHRISTMAS.getLabel(), applyChristmasEventDiscount()
        );
    }

    // 크리스마스 이벤트 할인
    private int applyChristmasEventDiscount() {
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