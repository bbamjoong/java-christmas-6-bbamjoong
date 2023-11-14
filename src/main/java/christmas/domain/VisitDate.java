package christmas.domain;

import static christmas.domain.enums.Constraints.END_DATE;
import static christmas.domain.enums.Constraints.START_DATE;

import christmas.exceptions.InvalidDateException;

public record VisitDate(int date) {

    public static VisitDate of(String date) {
        int dateValue = validateVisitDate(date);
        return new VisitDate(dateValue);
    }

    private static int validateVisitDate(String date) {
        int value = inputToNumber(date);
        validateDateRange(value);
        return value;
    }

    // 정수형으로 입력되지 않으면 예외 발생
    private static int inputToNumber(String date) {
        try {
            return Integer.parseInt(date);
        } catch (NumberFormatException e) {
            throw new InvalidDateException();
        }
    }

    // 1 이상 31 이하가 아닐 경우 예외 발생
    private static void validateDateRange(int value) {
        if (value < START_DATE.getValue() || value > END_DATE.getValue()) {
            throw new InvalidDateException();
        }
    }
}