package christmas.domain;

import static christmas.domain.enums.Constraints.WEEK_DISCOUNT;

import christmas.domain.enums.Menu;
import christmas.domain.enums.MenuCategory;
import christmas.exceptions.InvalidOrderException;
import java.util.Arrays;

public record Food(String name, int count, int price, MenuCategory menuCategory) {
    public static Food of(String name, String count) {
        Menu menu = getMenuByName(name);
        int itemCount = validateCount(count);
        return new Food(name, itemCount, menu.getPrice(), menu.getMenuCategory());
    }

    // 이름으로 Menu 찾기
    private static Menu getMenuByName(String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(InvalidOrderException::new);
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

    // 카테고리 일치 할 경우 할인 금액 계산
    public boolean checkCategory(MenuCategory category) {
        return menuCategory == category;
    }

    // 할인 금액 계산
    public int calculateDiscount() {
        return count * WEEK_DISCOUNT.getValue();
    }

    // 음식의 총 가격 계산
    public int calculateFoodPrice() {
        return count * price;
    }
}