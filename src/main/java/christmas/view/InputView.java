package christmas.view;

import static christmas.view.ViewMessage.REQUIRE_MENU_MESSAGE;
import static christmas.view.ViewMessage.REQUIRE_VISIT_DATE_MESSAGE;

import camp.nextstep.edu.missionutils.Console;
import christmas.exceptions.InvalidDateException;
import christmas.exceptions.InvalidOrderException;

public class InputView {
    public static String printVisitDate() {
        System.out.println(REQUIRE_VISIT_DATE_MESSAGE.getMessage());
        String input = Console.readLine();
        validate(input, new InvalidDateException());
        return input;
    }

    public static String printMenu() {
        System.out.println(REQUIRE_MENU_MESSAGE.getMessage());
        String input = Console.readLine();
        validate(input, new InvalidOrderException());
        return input;
    }

    // 최소한의 입력 값 검증
    private static void validate(String input, IllegalArgumentException exception) {
        validateNull(input, exception);
        validateBlank(input, exception);
    }

    // Null 검증
    private static void validateNull(String input, IllegalArgumentException exception) {
        if (input == null) {
            throw exception;
        }
    }

    // 빈 값 + 공백 검증
    private static void validateBlank(String input, IllegalArgumentException exception) {
        if (input.isBlank()) {
            throw exception;
        }
    }
}