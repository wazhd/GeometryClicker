package com.example.geometryclicker;

public class Pyramid {
    private static final double PYRAMID_UPS_PER_PURCHASE_SA = calculateSurfaceArea(1, 1);
    private static final double PYRAMID_UPS_PER_PURCHASE_VOLUME = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 1.5;
    public static final int INITIAL_PRICE = 150000;
    public static final int UPGRADE_PRICE = 200000;

    public static int pyramids = 0;
    public static double pyramid_UPS = 0;
    public static double pyramid_price = INITIAL_PRICE;

    public static String getFormattedPrice() {
        return Util.formatNumber(pyramid_price);
    }

    public static String getFormattedUpgradePrice() {
        return Util.formatNumber(UPGRADE_PRICE);
    }

    public static double calculateSurfaceArea(double base, double height) {
        double slantHeight = Math.sqrt((base / 2.0) * (base / 2.0) + height * height);
        return base * base + 2 * base * slantHeight;
    }

    public static double calculateVolume(double base, double height) {
        return (1.0/3.0) * base * base * height;
    }

    public static double buyPyramid(double sideLength, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(sideLength, sideLength);
            pyramid_UPS += PYRAMID_UPS_PER_PURCHASE_VOLUME;
        } else {
            currentTotal = calculateSurfaceArea(sideLength, sideLength);
            pyramid_UPS += PYRAMID_UPS_PER_PURCHASE_SA;
        }

        currentTotal -= pyramid_price;

        double newSideLength;
        if (upgradeForVolume) {
            newSideLength = Math.pow(3.0 * currentTotal, 1.0/3.0);
        } else {
            double a = 2.0;
            double b = 1.0;
            double c = -currentTotal;
            newSideLength = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
        }

        pyramids++;
        pyramid_price = calculatePriceFromQuantity(INITIAL_PRICE, pyramids);

        return newSideLength;
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}