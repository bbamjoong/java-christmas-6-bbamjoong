package christmas.service;

import static christmas.domain.enums.Constraints.FREE_GIFT_PRICE_THRESHOLD;

import christmas.domain.DiscountCalculator;
import christmas.domain.Food;
import christmas.domain.Foods;
import christmas.domain.Parser;
import christmas.domain.VisitDate;
import christmas.domain.enums.Badge;
import christmas.domain.enums.DiscountType;
import java.util.List;
import java.util.Map;

public class ChristmasService {
    // 방문 날짜 객체 생성
    public VisitDate getVisitDate(String input) {
        return VisitDate.of(input);
    }

    // Foods 객체 생성
    public Foods getFoods(String input) {
        Map<String, String> parsedInput = Parser.parseInput(input);
        List<Food> orderedFoods = createOrderedFoods(parsedInput);
        return Foods.of(orderedFoods);
    }

    // Food 객체 List 생성
    private List<Food> createOrderedFoods(Map<String, String> parsedInput) {
        return parsedInput.entrySet().stream()
                .map(entry -> Food.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    // 증정 메뉴 지급 판별
    public Boolean findFreeGift(int totalPrice) {
        return totalPrice >= FREE_GIFT_PRICE_THRESHOLD.getValue();
    }

    // 할인 내역 Map 반환
    public Map<DiscountType, Integer> calculateDiscountsMap(DiscountCalculator discountCalculator) {
        return discountCalculator.calculateDiscount();
    }

    // 총 할인 금액 계산
    public int calculateDiscounts(Map<DiscountType, Integer> discounts) {
        return discounts.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    // 증정품을 제외한 할인금액 계산
    public int calculateDiscountExceptFreeGift(Map<DiscountType, Integer> discounts) {
        return discounts.entrySet()
                .stream()
                .filter(entry -> entry.getKey() != DiscountType.BONUS_GIFT)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    // 배지 정보 계산
    public String getBadge(int discountsPrice) {
        Badge badge = Badge.getBadgeForPrice(discountsPrice);
        return badge.getName();
    }
}