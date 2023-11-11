package christmas.exceptions;

public enum ErrorMessage {
    PREFIX("[ERROR]"),
    INVALID_ORDER_MESSAGE("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX.message + " " + message;
    }
}
