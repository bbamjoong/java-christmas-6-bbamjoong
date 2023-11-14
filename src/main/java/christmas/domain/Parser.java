package christmas.domain;

import static christmas.domain.enums.Constraints.PAIR_SIZE;

import christmas.exceptions.InvalidOrderException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public static Map<String, String> parseInput(String input) {
        List<String> commaSeparated = parseByComma(input);
        List<String> hyphenSeparated = parseByHyphen(commaSeparated);
        return createMap(hyphenSeparated);
    }

    // 콤마를 기준으로 파싱
    private static List<String> parseByComma(String input) {
        return Arrays.stream(input.split(",", -1))
                .map(String::trim)
                .toList();
    }

    // 하이픈을 기준으로 파싱
    private static List<String> parseByHyphen(List<String> items) {
        return items.stream()
                .flatMap(item -> validateHyphen(item).stream())
                .map(String::trim)
                .toList();
    }

    private static List<String> validateHyphen(String item) {
        List<String> splitItem = List.of(item.split("-", -1));
        if (splitItem.size() != PAIR_SIZE.getValue()) {
            throw new InvalidOrderException();
        }
        return splitItem;
    }

    // 파싱된 List로 Map 생성
    private static Map<String, String> createMap(List<String> itemList) {
        Map<String, String> resultMap = new HashMap<>();
        addItems(resultMap, itemList);
        return resultMap;
    }

    // 중복 키 검증 후 값 추가
    private static void addItems(Map<String, String> map, List<String> itemList) {
        for (int i = 0; i < itemList.size(); i += PAIR_SIZE.getValue()) {
            String key = itemList.get(i);
            String value = itemList.get(i + 1);

            validateDuplicateKey(map, key);
            map.put(key, value);
        }
    }

    // 중복된 Key 존재 시 예외 발생
    private static void validateDuplicateKey(Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            throw new InvalidOrderException();
        }
    }
}