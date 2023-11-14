package christmas.domain;

import static christmas.domain.enums.Constraints.COUNT_THRESHOLD;

import christmas.domain.enums.MenuCategory;
import christmas.exceptions.InvalidOrderException;
import java.util.List;

public record Foods(List<Food> orderFoods) {
    public static Foods of(List<Food> foods) {
        validateCountSum(foods);
        return new Foods(foods);
    }

    // 메뉴들의 개수 합이 20개가 넘으면 예외 발생
    private static void validateCountSum(List<Food> foods) {
        int totalCount = calculateTotalCount(foods);

        if (totalCount > COUNT_THRESHOLD.getValue()) {
            throw new InvalidOrderException();
        }
    }

    // 메뉴들의 개수 합을 구하는 기능
    private static int calculateTotalCount(List<Food> foods) {
        return foods.stream().mapToInt(Food::count).sum();
    }

    // 음식 전체 가격 계산
    public int calculateTotalPrice() {
        return orderFoods.stream()
                .mapToInt(Food::calculateFoodPrice)
                .sum();
    }

    // 카테고리별 할인 금액 계산
    public int calculateDiscountByCategory(MenuCategory category) {
        return orderFoods.stream()
                .filter(food -> food.checkCategory(category))
                .mapToInt(Food::calculateDiscount)
                .sum();
    }
}