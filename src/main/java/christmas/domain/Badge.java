package christmas.domain;

public enum Badge {
    SANTA(20000, "산타"),
    TREE(10000, "트리"),
    STAR(5000, "별"),
    NONE(0, "없음");

    private final int price;
    private final String name;

    Badge(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public static Badge getBadgeForPrice(int finalPrice) {
        for (Badge badge : Badge.values()) {
            if (finalPrice >= badge.price) {
                return badge;
            }
        }
        return NONE;
    }

    public String getName() {
        return name;
    }
}
