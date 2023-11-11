package christmas.domain;

import christmas.exceptions.InvalidOrderException;

public record Food(String name, int count, int price) {
    public static Food of(String name, String count) {
        Menu menu = getMenuFromName(name);
        MenuItem item = getItemFromMenu(name, menu);
        int itemCount = validateCount(count);
        return new Food(name, itemCount, item.price());
    }

    // 이름으로 메뉴 가져오기
    private static Menu getMenuFromName(String name) {
        for (Menu menu : Menu.values()) {
            if (isItemInMenu(name, menu)) {
                return menu;
            }
        }
        throw new InvalidOrderException();
    }

    // 메뉴에서 아이템 가져오기
    private static MenuItem getItemFromMenu(String name, Menu menu) {
        return menu.getItems().stream()
                .filter(menuItem -> menuItem.name().equals(name))
                .findFirst()
                .orElseThrow(InvalidOrderException::new);
    }

    // 메뉴에 아이템이 있는지 확인
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