package christmas.domain;

public enum Constraints {
    //메뉴, 개수 입력 검증
    COUNT_THRESHOLD(20),
    PAIR_SIZE(2),

    // 방문 날짜 + 이벤트 기간
    START_DATE(1),
    END_DATE(31),

    //크리스마스 이벤트
    CHRISTMAS_EVENT_START_DATE(1),
    CHRISTMAS_EVENT_END_DATE(25),
    BASE_DISCOUNT(1_000),
    DISCOUNT_PER_DAY(100),

    // 특별 할인
    SPECIAL_DISCOUNT(1_000),

    // 주간, 주말 할인 금액
    WEEK_DISCOUNT(2_023),

    // 증정품 제공 기준 금액
    FREE_GIFT_PRICE_THRESHOLD(120_000),

    // 0
    ZERO(0);

    private final int value;

    Constraints(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}