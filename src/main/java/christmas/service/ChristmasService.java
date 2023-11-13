package christmas.service;

import christmas.domain.VisitDate;

public class ChristmasService {
    // 방문 날짜 객체 생성
    public VisitDate getVisitDate(String input) {
        return VisitDate.of(input);
    }
}