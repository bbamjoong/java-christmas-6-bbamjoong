package christmas.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.exceptions.InvalidOrderException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FoodsTest {
    List<Food> validFoods;
    List<Food> invalidFoods;

    @BeforeEach
    void setUp() {
        validFoods = new ArrayList<>();
        validFoods.add(Food.of("양송이수프", "10"));
        validFoods.add(Food.of("제로콜라", "10"));

        invalidFoods = new ArrayList<>();
        invalidFoods.add(Food.of("양송이수프", "10"));
        invalidFoods.add(Food.of("제로콜라", "20"));
    }

    @Test
    @DisplayName("주문 개수가 20이 넘으면 예외 발생")
    void overThresholdCountTest() {
        assertThatThrownBy(() -> Foods.of(invalidFoods))
                .isInstanceOf(InvalidOrderException.class);
    }

    @Test
    @DisplayName("주문 개수가 20이 넘지 않으면 정상 작동")
    void correctCountTest() {
        assertThatCode(() -> Foods.of(validFoods))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("양송이수프 10개, 제로콜라 10개는 9만원")
    void calculateTotalPriceTest() {
        Foods foods = Foods.of(validFoods);
        int result = foods.calculateTotalPrice();

        assertThat(result).isEqualTo(90000);
    }
}