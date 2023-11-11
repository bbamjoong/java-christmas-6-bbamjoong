package christmas.domain;

import christmas.exceptions.InvalidOrderException;

public record Food(String name, int count) {
    public static Food of(String name, String count) {
        return new Food(validateMenuExistence(name), validateCount(count));
    }

    // 메뉴판에 존재하지 않는 메뉴일 경우 예외 발생
    private static String validateMenuExistence(String name) {
        for (Menu menu : Menu.values()) {
            if (isItemInMenu(name, menu)) {
                return name;
            }
        }
        throw new InvalidOrderException();
    }

    // 메뉴판에 존재하는 메뉴인지 확인
    private static boolean isItemInMenu(String name, Menu menu) {
        return menu.getItems().stream()
                .anyMatch(menuItem -> menuItem.name().equals(name));
    }

    // 정수 검증
    private static int validateCount(String count) {
        int value = inputToNumber(count);
        validatePositive(value);
        return value;
    }

    // 정수형으로 입력되지 않으면 예외 발생
    private static int inputToNumber(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidOrderException();
        }
    }

    // 1 이상의 값이 아닐 경우 예외 발생
    private static void validatePositive(int value) {
        if (value <= 0) {
            throw new InvalidOrderException();
        }
    }
}