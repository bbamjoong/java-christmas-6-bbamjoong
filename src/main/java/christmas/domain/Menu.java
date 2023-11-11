package christmas.domain;

import java.util.List;

public enum Menu {
    APPETIZER("애피타이저", List.of(
            new MenuItem("양송이수프", 6000),
            new MenuItem("타파스", 5500),
            new MenuItem("시저샐러드", 8000)
    )),
    MAIN("메인", List.of(
            new MenuItem("티본스테이크", 55000),
            new MenuItem("바비큐립", 54000),
            new MenuItem("해산물파스타", 35000),
            new MenuItem("크리스마스파스타", 25000)
    )),
    DESSERT("디저트", List.of(
            new MenuItem("초코케이크", 15000),
            new MenuItem("아이스크림", 5000)
    )),
    BEVERAGE("음료", List.of(
            new MenuItem("제로콜라", 3000),
            new MenuItem("레드와인", 60000),
            new MenuItem("샴페인", 25000)
    ));

    private final String category;
    private final List<MenuItem> items;

    Menu(String category, List<MenuItem> items) {
        this.category = category;
        this.items = items;
    }

    public String getCategory() {
        return category;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
