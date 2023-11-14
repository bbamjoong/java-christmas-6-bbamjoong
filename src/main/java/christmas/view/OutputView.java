package christmas.view;

import static christmas.domain.enums.Constraints.ZERO;
import static christmas.view.ViewMessage.BADGE_MESSAGE;
import static christmas.view.ViewMessage.BENEFITS_MESSAGE;
import static christmas.view.ViewMessage.BENEFITS_PRESET;
import static christmas.view.ViewMessage.DISCOUNTS_MESSAGE;
import static christmas.view.ViewMessage.DISCOUNTS_PRESET;
import static christmas.view.ViewMessage.FINAL_AMOUNT_MESSAGE;
import static christmas.view.ViewMessage.FINAL_AMOUNT_PRESET;
import static christmas.view.ViewMessage.FOOD_PRESET;
import static christmas.view.ViewMessage.FREE_GIFT_MESSAGE;
import static christmas.view.ViewMessage.GIFT_MENU;
import static christmas.view.ViewMessage.NOTHING;
import static christmas.view.ViewMessage.ORDER_MENU_MESSAGE;
import static christmas.view.ViewMessage.PREVIEW_EVENT_BENEFITS_MESSAGE;
import static christmas.view.ViewMessage.PRICE;
import static christmas.view.ViewMessage.TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE;

import christmas.domain.Foods;
import christmas.domain.enums.DiscountType;
import java.util.Map;

public class OutputView {
    public static void printPreviewEventBenefits() {
        System.out.println(PREVIEW_EVENT_BENEFITS_MESSAGE.getMessage());
    }

    public static void printFoods(Foods foods) {
        System.out.println(ORDER_MENU_MESSAGE.getMessage());
        foods.orderFoods().forEach(orderFood -> printSingleFood(orderFood.name(), orderFood.count()));
    }

    private static void printSingleFood(String name, int count) {
        System.out.printf(FOOD_PRESET.getMessage(), name, count);
    }

    public static void printPrice(int totalPrice) {
        System.out.println(TOTAL_PRICE_BEFORE_DISCOUNT_MESSAGE.getMessage());
        System.out.printf(PRICE.getMessage(), totalPrice);
    }

    public static void printGiftMenu(Boolean giveGift) {
        System.out.println(FREE_GIFT_MESSAGE.getMessage());
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
        System.out.println(BENEFITS_MESSAGE.getMessage());
        printNothing();
    }

    public static void printDiscounts(Map<DiscountType, Integer> discountsInfo) {
        System.out.println(BENEFITS_MESSAGE.getMessage());
        discountsInfo.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != ZERO.getValue())
                .forEach(entry -> System.out.printf(BENEFITS_PRESET.getMessage(), entry.getKey().getMessage(),
                        -entry.getValue()));
    }

    public static void printDiscountsAmount(int discounts) {
        System.out.println(DISCOUNTS_MESSAGE.getMessage());
        System.out.printf(DISCOUNTS_PRESET.getMessage(), -discounts);
    }

    public static void printFinalAmount(int finalAmount) {
        System.out.println(FINAL_AMOUNT_MESSAGE.getMessage());
        System.out.printf(FINAL_AMOUNT_PRESET.getMessage(), finalAmount);
    }

    public static void printBadgeName(String badgeName) {
        System.out.println(BADGE_MESSAGE.getMessage());
        System.out.println(badgeName);
    }
}