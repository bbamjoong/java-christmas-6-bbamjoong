package christmas.domain;

public enum DiscountType {
    CHRISTMAS("ChristmasEvent"),
    WEEKDAY("WeekdayDiscount"),
    WEEKEND("WeekendDiscount"),
    SPECIAL("SpecialDayDiscount"),
    BONUS_GIFT("BonusGiftDiscount");

    private final String label;

    DiscountType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
