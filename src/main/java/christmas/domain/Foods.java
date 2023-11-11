package christmas.domain;

import static christmas.domain.Constraints.COUNT_THRESHOLD;

import christmas.exceptions.InvalidOrderException;
import java.util.List;

public class Foods {
    private List<Food> foods;

    private Foods(List<Food> foods) {
        this.foods = foods;
    }

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
}