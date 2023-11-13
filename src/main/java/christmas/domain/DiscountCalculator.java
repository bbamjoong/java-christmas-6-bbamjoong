package christmas.domain;

import static christmas.domain.Constraints.BASE_DISCOUNT;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_END_DATE;
import static christmas.domain.Constraints.CHRISTMAS_EVENT_START_DATE;
import static christmas.domain.Constraints.DISCOUNT_PER_DAY;
import static christmas.domain.Constraints.EVENT_MONTH;
import static christmas.domain.Constraints.EVENT_YEAR;
import static christmas.domain.Constraints.FREE_GIFT_PRICE_THRESHOLD;
import static christmas.domain.Constraints.SPECIAL_DISCOUNT;
import static christmas.domain.Constraints.WEEK_DISCOUNT;
import static christmas.domain.Constraints.ZERO;
import static christmas.domain.DiscountType.BONUS_GIFT;
import static christmas.domain.DiscountType.CHRISTMAS;
import static christmas.domain.DiscountType.SPECIAL;
import static christmas.domain.DiscountType.WEEKDAY;
import static christmas.domain.DiscountType.WEEKEND;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class DiscountCalculator {
    private final int visitDay;
    private final Foods foods;

    public DiscountCalculator(Foods foods, int visitDay) {
        this.foods = foods;
        this.visitDay = visitDay;
    }

    public Map<DiscountType, Integer> calculateDiscount() {
        return Map.of(
                CHRISTMAS, applyChristmasEventDiscount(),
                WEEKDAY, applyWeekdayDiscount(),
                WEEKEND, applyWeekendDiscount(),
                SPECIAL, applySpecialDayDiscount(),
                BONUS_GIFT, applyBonusGiftDiscount()
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


    // 주간 메인메뉴 할인
    private int applyWeekdayDiscount() {
        DayOfWeek dayOfWeek = LocalDate.of(EVENT_YEAR.getValue(), EVENT_MONTH.getValue(), visitDay).getDayOfWeek();
        if (dayOfWeek != DayOfWeek.FRIDAY && dayOfWeek != DayOfWeek.SATURDAY) {
            return calculateDiscountByCategory(MenuCategory.MAIN);
        }
        return ZERO.getValue();
    }

    // 주말 메인메뉴 할인
    private int applyWeekendDiscount() {
        DayOfWeek dayOfWeek = LocalDate.of(EVENT_YEAR.getValue(), EVENT_MONTH.getValue(), visitDay).getDayOfWeek();
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) {
            return calculateDiscountByCategory(MenuCategory.DESSERT);
        }
        return ZERO.getValue();
    }

    // 해당 카테고리 메뉴 할인
    private int calculateDiscountByCategory(MenuCategory category) {
        return foods.orderFoods().stream()
                .filter(food -> food.menuCategory() == category)
                .mapToInt(food -> food.count() * WEEK_DISCOUNT.getValue())
                .sum();
    }

    // 특별 할인
    private int applySpecialDayDiscount() {
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

    // 총 구매액이 120_000원 이상일 경우 샴페인 증정
    private int applyBonusGiftDiscount() {
        if (foods.calculateTotalPrice() >= FREE_GIFT_PRICE_THRESHOLD.getValue()) {
            return Menu.CHAMPAGNE.getPrice();
        }
        return ZERO.getValue();
    }
}