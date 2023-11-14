package christmas.domain;

import christmas.domain.enums.Menu;
import christmas.domain.enums.MenuCategory;
import christmas.exceptions.InvalidOrderException;

public record Food(String name, int count, int price, MenuCategory menuCategory) {
    public static Food of(String name, String count) {
        Menu menu = getMenuByName(name);
        int itemCount = validateCount(count);
        return new Food(name, itemCount, menu.getPrice(), menu.getMenuCategory());
    }

    private static Menu getMenuByName(String name) {
        for (Menu menu : Menu.values()) {
            if (menu.getName().equalsIgnoreCase(name)) {
                return menu;
            }
        }
        throw new InvalidOrderException();
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