package christmas.controller;

import christmas.domain.Foods;
import christmas.domain.VisitDate;
import christmas.service.ChristmasService;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.function.Supplier;

public class ChristmasController {
    private final ChristmasService christmasService;

    public ChristmasController() {
        this.christmasService = new ChristmasService();
    }

    public void run() {
        VisitDate visitDate = getValidVisitDate();
        Foods foods = getValidFoods();
        printPreviewBenefitsMessage();
        printOrderMenu(foods);
    }

    // 방문 날짜 객체 생성
    private VisitDate getValidVisitDate() {
        return getValidInput(this::createVisitDate);
    }

    private VisitDate createVisitDate() {
        String input = InputView.printVisitDate();
        return christmasService.getVisitDate(input);
    }

    // Foods 객체 생성
    public Foods getValidFoods() {
        return getValidInput(this::createFoods);
    }

    private Foods createFoods() {
        String input = InputView.printMenu();
        return christmasService.getFoods(input);
    }

    // 이벤트 혜택 미리보기 메시지 출력
    private void printPreviewBenefitsMessage() {
        OutputView.printPreviewEventBenefits();
    }

    // 주문 메뉴 출력
    private void printOrderMenu(Foods foods) {
        OutputView.printFoods(foods);
    }

    // 함수형 인터페이스
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