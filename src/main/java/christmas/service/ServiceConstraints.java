package christmas.service;

public enum ServiceConstraints {
    GIFT_MENU("샴페인 1개\n"),

    // 없음
    NOTHING("없음\n");

    private final String message;

    ServiceConstraints(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
