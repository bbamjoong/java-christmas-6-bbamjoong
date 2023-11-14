package christmas.view;

public enum ViewMessage {
    // InputView Message
    REQUIRE_VISIT_DATE_MESSAGE("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n"
            + "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    REQUIRE_MENU_MESSAGE("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),
    PREVIEW_EVENT_BENEFITS_MESSAGE("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),

    // OutputView Message
    // 주문 메뉴
    ORDER_MENU_MESSAGE("\n<주문 메뉴>"),
    FOOD_PRESET("%s %d개\n"),

    // 할인 전 총주문 금액
    TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE("\n<할인 전 총주문 금액>"),
    PRICE("%,d원\n"),

    // 증정 메뉴
    FREE_GIFT_MESSAGE("\n<증정 메뉴>"),
    GIFT_MENU("샴페인 1개"),

    // 혜택 내역
    BENEFITS_MESSAGE("\n<혜택 내역>"),
    BENEFITS_PRESET("%s: %,d원\n"),

    // 총 할인 금액
    DISCOUNTS_MESSAGE("\n<총혜택 금액>"),
    DISCOUNTS_PRESET("%,d원\n"),

    // 할인 후 최종 금액
    FINAL_AMOUNT_MESSAGE("\n<할인 후 예상 결제 금액>"),
    FINAL_AMOUNT_PRESET("%,d원\n"),

    // 배지
    BADGE_MESSAGE("\n<12월 이벤트 배지>"),

    // 없음
    NOTHING("없음");

    private final String message;

    ViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
