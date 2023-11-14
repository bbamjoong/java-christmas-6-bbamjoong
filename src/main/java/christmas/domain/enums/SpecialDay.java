package christmas.domain.enums;

public enum SpecialDay {
    NOVEMBER_3(3),
    NOVEMBER_10(10),
    NOVEMBER_17(17),
    NOVEMBER_24(24),
    NOVEMBER_25(25),
    NOVEMBER_31(31);

    private final int eventDay;

    SpecialDay(int eventDay) {
        this.eventDay = eventDay;
    }

    public int getValue() {
        return eventDay;
    }

    public boolean isSpecialDay(int visitDay) {
        return visitDay == this.eventDay;
    }
}
