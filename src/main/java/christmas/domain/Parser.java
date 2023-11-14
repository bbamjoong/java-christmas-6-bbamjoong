package christmas.domain;

import static christmas.domain.enums.Constraints.KEY_INDEX;
import static christmas.domain.enums.Constraints.PAIR_SIZE;
import static christmas.domain.enums.Constraints.VALUE_INDEX;

import christmas.exceptions.InvalidOrderException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    public static Map<String, String> parseInput(String input) {
        return splitByComma(input)
                .map(Parser::splitByHyphen)
                .collect(Collectors.toMap(item -> item.get(KEY_INDEX.getValue()),
                        item -> item.get(VALUE_INDEX.getValue()), Parser::validateDuplicatedKey));
    }

    // Delimiter 기준으로 분할
    private static Stream<String> splitByDelimiter(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter, -1))
                .map(String::trim);
    }

    // Comma 기준으로 분할
    private static Stream<String> splitByComma(String input) {
        return splitByDelimiter(input, ",");
    }

    // Hyphen 기준으로 분할
    private static List<String> splitByHyphen(String input) {
        List<String> splitItem = splitByDelimiter(input, "-").toList();

        validateHyphenSplit(splitItem);
        return splitItem;
    }

    // Hyphen 기준으로 분할 시 두 개의 값이 나오지 않으면 예외 발생
    private static void validateHyphenSplit(List<String> splitItem) {
        if (splitItem.size() != PAIR_SIZE.getValue()) {
            throw new InvalidOrderException();
        }
    }

    // 중복된 메뉴가 존재할 경우 예외 발생
    private static String validateDuplicatedKey(String existingValue, String newValue) {
        throw new InvalidOrderException();
    }
}