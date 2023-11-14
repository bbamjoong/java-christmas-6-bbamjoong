package christmas.domain.discountstrategies;

import static christmas.domain.enums.Constraints.EVENT_MONTH;
import static christmas.domain.enums.Constraints.EVENT_YEAR;
import static christmas.domain.enums.Constraints.ZERO;

import christmas.domain.Foods;
import christmas.domain.enums.MenuCategory;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendDiscount implements DiscountStrategy {
    private final Foods foods;
    private final int visitDay;

    private WeekendDiscount(Foods foods, int visitDay) {
        this.foods = foods;
        this.visitDay = visitDay;
    }

    public static WeekendDiscount of(Foods foods, int visitDay) {
        return new WeekendDiscount(foods, visitDay);
    }

    @Override
    // 주말 메인메뉴 할인
    public int calculate() {
        DayOfWeek dayOfWeek = LocalDate.of(EVENT_YEAR.getValue(), EVENT_MONTH.getValue(), visitDay).getDayOfWeek();
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) {
            return foods.calculateDiscountByCategory(MenuCategory.DESSERT);
        }
        return ZERO.getValue();
    }
}