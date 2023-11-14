package christmas.service;

import static christmas.domain.enums.Badge.NONE;
import static christmas.domain.enums.Badge.SANTA;
import static christmas.domain.enums.Badge.STAR;
import static christmas.domain.enums.Badge.TREE;
import static christmas.domain.enums.Constraints.BASE_DISCOUNT;
import static christmas.domain.enums.Menu.CHAMPAGNE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.domain.DiscountCalculator;
import christmas.domain.Food;
import christmas.domain.Foods;
import christmas.domain.VisitDate;
import christmas.domain.enums.DiscountType;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ChristmasServiceTest {
    ChristmasService christmasService;

    @BeforeEach
    void setUp() {
        christmasService = new ChristmasService();
    }

    @ParameterizedTest(name = "{index}: {0}")
    @ValueSource(strings = {"1", "25", "31"})
    @DisplayName("입력한 날짜에 따라 VisitDate 객체 생성 테스트")
    void getVisitDateTest(String input) {
        VisitDate result = christmasService.getVisitDate(input);
        VisitDate expected = VisitDate.of(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Foods 객체 생성 테스트")
    void getFoodsTest() {
        String input = "티본스테이크-2,초코케이크-2";

        Foods result = christmasService.getFoods(input);
        Foods expected = Foods.of(List.of(
                Food.of("티본스테이크", "2"),
                Food.of("초코케이크", "2")
        ));

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @DisplayName("120,000원 이상 증정품 선물")
    @ValueSource(ints = {120_000, 150_000})
    void priceOverThresholdFreeGiftTest(int price) {
        assertThat(christmasService.findFreeGift(price)).isTrue();
    }

    @ParameterizedTest(name = "{index}: {0}")
    @DisplayName("120,000원 미만 증정품 X")
    @ValueSource(ints = {119_000, 100_000})
    void priceUnderThresholdFreeGiftTest(int price) {
        assertThat(christmasService.findFreeGift(price)).isFalse();
    }

    @Test
    @DisplayName("1일은 크리스마스 1000원 할인정 보가 Map에 존재")
    void calculateDiscountsMapTest() {
        Foods orderedFoods = Foods.of(List.of(
                Food.of("티본스테이크", "2")));

        DiscountCalculator discountCalculator = DiscountCalculator.of(orderedFoods, 1);
        Map<DiscountType, Integer> discounts = christmasService.calculateDiscountsInfo(discountCalculator);

        assertThat(discounts.containsValue(BASE_DISCOUNT.getValue())).isTrue();
    }

    @Test
    @DisplayName("총 할인 금액 계산")
    void calculateDiscountsTest() {
        Map<DiscountType, Integer> discounts = Map.of(
                DiscountType.CHRISTMAS, 1000,
                DiscountType.WEEKDAY, 2000
        );

        int result = christmasService.calculateDiscounts(discounts);
        int expected = 3000;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("할인 후 예상 결제 금액 계산(totalPrice 150_000원)")
    void calculateFinalPriceTest() {
        Map<DiscountType, Integer> discounts = Map.of(
                DiscountType.CHRISTMAS, 1000,
                DiscountType.BONUS_GIFT, CHAMPAGNE.getPrice()
        );

        int result = christmasService.calculateFinalPrice(discounts, 150_000);
        int expected = 149_000;

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("badgeParameter")
    @DisplayName("금액에 따른 배지 테스트")
    void getBadgeTest(String testName, int totalDiscounts, String expectedBadgeName) {
        String result = christmasService.getBadge(totalDiscounts);
        assertThat(result).isEqualTo(expectedBadgeName);
    }

    private static Stream<Arguments> badgeParameter() {
        return Stream.of(
                Arguments.of("4,900원: 없음", 4_900, NONE.getName()),
                Arguments.of("5,000원: 별", 5_000, STAR.getName()),
                Arguments.of("9,900원: 별", 9_900, STAR.getName()),
                Arguments.of("10,000원: 트리", 10_000, TREE.getName()),
                Arguments.of("19,990원: 트리", 19_900, TREE.getName()),
                Arguments.of("20,000원: 산타", 20_000, SANTA.getName()),
                Arguments.of("100,000원: 산타", 100_000, SANTA.getName())
        );
    }
}
