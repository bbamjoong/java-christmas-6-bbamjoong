package christmas.domain;

public enum Constraints {
    COUNT_THRESHOLD(20),
    PAIR_SIZE(2),
    START_DATE(1),
    END_DATE(31);
    private final int value;

    Constraints(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
