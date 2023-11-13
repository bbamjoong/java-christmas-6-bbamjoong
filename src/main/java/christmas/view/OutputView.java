package christmas.view;

import static christmas.view.ViewMessage.BENEFITS_LIST;
import static christmas.view.ViewMessage.BENEFITS_PRESET;
import static christmas.view.ViewMessage.FOOD_PRESET;
import static christmas.view.ViewMessage.FREE_GIFT;
import static christmas.view.ViewMessage.GIFT_MENU;
import static christmas.view.ViewMessage.NOTHING;
import static christmas.view.ViewMessage.ORDER_MENU;
import static christmas.view.ViewMessage.PREVIEW_EVENT_BENEFITS;
import static christmas.view.ViewMessage.PRICE;
import static christmas.view.ViewMessage.TOTAL_PRICE_BEFORE_DISCOUNT;

import christmas.domain.DiscountType;
import christmas.domain.Foods;
import java.util.Map;

public class OutputView {
    public static void printPreviewEventBenefits() {
        System.out.println(PREVIEW_EVENT_BENEFITS.getMessage());
    }

    public static void printFoods(Foods foods) {
        System.out.println(ORDER_MENU.getMessage());
        foods.orderFoods().forEach(orderFood -> printSingleFood(orderFood.name(), orderFood.count()));
    }

    private static void printSingleFood(String name, int count) {
        System.out.printf(FOOD_PRESET.getMessage(), name, count);
    }

    public static void printPrice(int totalPrice) {
        System.out.println(TOTAL_PRICE_BEFORE_DISCOUNT.getMessage());
        System.out.printf(PRICE.getMessage(), totalPrice);
    }

    public static void printGiftMenu(Boolean giveGift) {
        System.out.println(FREE_GIFT.getMessage());
        findGift(giveGift);
    }

    private static void findGift(Boolean giveGift) {
        if (giveGift) {
            System.out.println(GIFT_MENU.getMessage());
            return;
        }
        printNothing();
    }

    private static void printNothing() {
        System.out.println(NOTHING.getMessage());
    }

    public static void printNoDiscounts() {
        System.out.println(BENEFITS_LIST.getMessage());
        printNothing();
    }

    public static void printDiscounts(Map<DiscountType, Integer> discountsInfo) {
        System.out.println(BENEFITS_LIST.getMessage());
        discountsInfo.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .forEach(entry -> System.out.printf(BENEFITS_PRESET.getMessage(), entry.getKey().getMessage(),
                        entry.getValue()));
    }
}