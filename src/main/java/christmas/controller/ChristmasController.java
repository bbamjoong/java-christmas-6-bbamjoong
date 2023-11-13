package christmas.controller;

import christmas.domain.Constraints;
import christmas.domain.DiscountCalculator;
import christmas.domain.DiscountType;
import christmas.domain.Foods;
import christmas.domain.VisitDate;
import christmas.service.ChristmasService;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.Map;
import java.util.function.Supplier;

public class ChristmasController {
    private final ChristmasService christmasService;
    private VisitDate visitDate;
    private Foods foods;

    public ChristmasController() {
        this.christmasService = new ChristmasService();
    }

    public void run() {
        handleDecemberEvent();

        int totalPrice = calculateTotalPrice();
        Map<DiscountType, Integer> discountsInfo = calculateDiscountsInfo();
        int finalPrice = calculateFinalPrice(totalPrice, discountsInfo);

        printEventDetails(totalPrice, discountsInfo, finalPrice);
    }

    // 12월 이벤트 처리
    private void handleDecemberEvent() {
        visitDate = getValidVisitDate();
        foods = getValidFoods();
        printPreviewBenefitsMessage();
        printOrderMenu(foods);
    }

    // 유효한 방문 날짜 가져오기
    private VisitDate getValidVisitDate() {
        return getValidInput(() -> christmasService.getVisitDate(InputView.printVisitDate()));
    }

    // 유효한 음식 가져오기
    private Foods getValidFoods() {
        return getValidInput(() -> christmasService.getFoods(InputView.printMenu()));
    }

    // 혜택 미리보기 메시지 출력
    private void printPreviewBenefitsMessage() {
        OutputView.printPreviewEventBenefits();
    }

    // 주문 메뉴 출력
    private void printOrderMenu(Foods foods) {
        OutputView.printFoods(foods);
    }

    // 총 주문 가격 계산
    private int calculateTotalPrice() {
        return foods.calculateTotalPrice();
    }

    // 할인 정보 계산
    private Map<DiscountType, Integer> calculateDiscountsInfo() {
        return christmasService.calculateDiscountsMap(
                DiscountCalculator.of(foods, visitDate.date())
        );
    }

    // 총 할인 금액 계산
    private int calculateTotalDiscounts(Map<DiscountType, Integer> discountsInfo) {
        return christmasService.calculateDiscounts(discountsInfo);
    }

    // 최종 결제 예정 가격 계산
    private int calculateFinalPrice(int totalPrice, Map<DiscountType, Integer> discountsInfo) {
        int discountsExceptFreeGift = christmasService.calculateDiscountExceptFreeGift(discountsInfo);
        return totalPrice - discountsExceptFreeGift;
    }

    // 이벤트 세부 정보 출력
    private void printEventDetails(int totalPrice, Map<DiscountType, Integer> discountsInfo, int finalPrice) {
        int totalDiscounts = calculateTotalDiscounts(discountsInfo);

        printEventBenefits(totalPrice);
        printEventResult(finalPrice, totalDiscounts);
        printDiscountInformation(discountsInfo, totalDiscounts);
    }

    // 이벤트 혜택 출력
    private void printEventBenefits(int totalPrice) {
        printTotalPriceBeforeDiscount(totalPrice);
        printFreeGift(totalPrice);
    }

    // 할인 정보 출력
    private void printDiscountInformation(Map<DiscountType, Integer> discountsInfo, int totalDiscounts) {
        printDiscountsInfo(discountsInfo, totalDiscounts);
        printDiscountsPrice(totalDiscounts);
    }

    // 최종 가격 출력
    private void printEventResult(int finalPrice, int totalDiscounts) {
        printFinalPrice(finalPrice);
        printBadge(totalDiscounts);
    }

    // 할인 전 총 가격 출력
    private void printTotalPriceBeforeDiscount(int totalPrice) {
        OutputView.printPrice(totalPrice);
    }

    // 증정품 출력
    private void printFreeGift(int totalPrice) {
        boolean freeGift = christmasService.findFreeGift(totalPrice);
        OutputView.printGiftMenu(freeGift);
    }

    // 할인 정보 출력
    private void printDiscountsInfo(Map<DiscountType, Integer> discountsInfo, int totalDiscounts) {
        if (totalDiscounts == Constraints.ZERO.getValue()) {
            OutputView.printNoDiscounts();
            return;
        }
        OutputView.printDiscounts(discountsInfo);
    }

    // 할인 금액 출력
    private void printDiscountsPrice(int totalDiscounts) {
        OutputView.printDiscountsAmount(totalDiscounts);
    }

    // 최종 결제 금액 출력
    private void printFinalPrice(int finalPrice) {
        OutputView.printFinalAmount(finalPrice);
    }

    // 배지 출력
    private void printBadge(int finalPrice) {
        String badgeName = christmasService.getBadge(finalPrice);
        OutputView.printBadgeName(badgeName);
    }

    private <T> T getValidInput(Supplier<T> method) {
        while (true) {
            try {
                return method.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}