package com.example.geometryclicker;

public class Pyramid {
    private static final double PYRAMID_UPS_PER_PURCHASE_SA_BASE = calculateSurfaceArea(1, 1);
    private static final double PYRAMID_UPS_PER_PURCHASE_SA_HEIGHT = calculateSurfaceArea(1, 1);
    private static final double PYRAMID_UPS_PER_PURCHASE_VOLUME_BASE = calculateVolume(1, 1);
    private static final double PYRAMID_UPS_PER_PURCHASE_VOLUME_HEIGHT = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 2.0;
    public static final double INITIAL_PRICE = calculateSurfaceArea(1, 1) * Util.scaleFactor * 100;
    public static final int UPGRADE_PRICE = 5000000;

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

    public static double[] buyPyramid(double base, double height, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(base, height);
            pyramid_UPS += PYRAMID_UPS_PER_PURCHASE_VOLUME_BASE + PYRAMID_UPS_PER_PURCHASE_VOLUME_HEIGHT;
        } else {
            currentTotal = calculateSurfaceArea(base, height);
            pyramid_UPS += PYRAMID_UPS_PER_PURCHASE_SA_BASE + PYRAMID_UPS_PER_PURCHASE_SA_HEIGHT;
        }

        currentTotal -= pyramid_price;

        double newBase = base + 1;
        double newHeight = height + 1;

        if (upgradeForVolume) {
            double volume = currentTotal;
            double tempBase = Math.pow((3.0 * volume) / newHeight, 0.5);
            if (tempBase > newBase) {
                newBase = tempBase;
            }
        } else {
            double surfaceArea = currentTotal;
            double slantHeight = Math.sqrt((newBase / 2.0) * (newBase / 2.0) + newHeight * newHeight);
            double a = 1.0;
            double b = Math.sqrt(newHeight * newHeight + 0.25);
            double c = -surfaceArea;
            double tempBase = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            if (tempBase > newBase) {
                newBase = tempBase;
            }
        }

        pyramids++;
        pyramid_price = calculatePriceFromQuantity(INITIAL_PRICE, pyramids);

        return new double[]{newBase, newHeight};
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}