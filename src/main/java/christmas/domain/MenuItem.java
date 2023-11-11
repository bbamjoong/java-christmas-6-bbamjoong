package christmas.domain;

public record MenuItem(String name, int price) {
    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }
}