package christmas.domain;

public enum Constraints {
    COUNT_THRESHOLD(20),
    PAIR_SIZE(2);

    private final int value;

    Constraints(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
