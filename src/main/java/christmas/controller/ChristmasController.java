package christmas.controller;

import christmas.domain.VisitDate;
import christmas.service.ChristmasService;
import christmas.view.InputView;
import java.util.function.Supplier;

public class ChristmasController {
    private final ChristmasService christmasService;

    public ChristmasController() {
        this.christmasService = new ChristmasService();
    }

    public void run() {
        VisitDate visitDate = getValidVisitDate();
    }

    // 방문 날짜 객체 생성
    private VisitDate getValidVisitDate() {
        return getValidInput(this::createVisitDate);
    }

    private VisitDate createVisitDate() {
        String input = InputView.printVisitDate();
        return christmasService.getVisitDate(input);
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