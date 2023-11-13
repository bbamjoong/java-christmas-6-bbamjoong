package christmas.view;

import static christmas.view.ViewMessage.FOOD_PRESET;
import static christmas.view.ViewMessage.ORDER_MENU;
import static christmas.view.ViewMessage.PREVIEW_EVENT_BENEFITS;
import static christmas.view.ViewMessage.PRICE;
import static christmas.view.ViewMessage.TOTAL_PRICE_BEFORE_DISCOUNT;

import christmas.domain.Foods;

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
}