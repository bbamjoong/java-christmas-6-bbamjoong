package christmas.service;

import static christmas.domain.Constraints.FREE_GIFT_PRICE_THRESHOLD;
import static christmas.service.ServiceConstraints.GIFT_MENU;
import static christmas.service.ServiceConstraints.NOTHING;

import christmas.domain.Food;
import christmas.domain.Foods;
import christmas.domain.Parser;
import christmas.domain.VisitDate;
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
}