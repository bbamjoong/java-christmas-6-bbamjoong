package christmas.view;

import static christmas.view.ViewMessage.REQUIRE_VISIT_DATE;

import camp.nextstep.edu.missionutils.Console;
import christmas.exceptions.InvalidDateException;

public class InputView {
    public static String printVisitDate() {
        System.out.println(REQUIRE_VISIT_DATE.getMessage());
        String input = Console.readLine();
        validate(input, new InvalidDateException());
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