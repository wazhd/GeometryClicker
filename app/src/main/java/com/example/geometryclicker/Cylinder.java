package com.example.geometryclicker;

public class Cylinder {
    private static final double CYLINDER_UPS_PER_PURCHASE_SA = calculateSurfaceArea(1, 1);
    private static final double CYLINDER_UPS_PER_PURCHASE_VOLUME = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 1.6;
    public static final int INITIAL_PRICE = 350000;
    public static final int UNLOCK_PRICE = 500000;

    public static int cylinders = 0;
    public static double cylinder_UPS = 0;
    public static double cylinder_price = INITIAL_PRICE;

    public static String getFormattedPrice() {
        return Util.formatNumber(cylinder_price);
    }

    public static String getFormattedUnlockPrice() {
        return Util.formatNumber(UNLOCK_PRICE);
    }

    public static double calculateSurfaceArea(double radius, double height) {
        return 2 * Math.PI * radius * (radius + height);
    }

    public static double calculateVolume(double radius, double height) {
        return Math.PI * radius * radius * height;
    }

    public static double buyCylinder(double sideLength, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(sideLength, sideLength);
            cylinder_UPS += CYLINDER_UPS_PER_PURCHASE_VOLUME;
        } else {
            currentTotal = calculateSurfaceArea(sideLength, sideLength);
            cylinder_UPS += CYLINDER_UPS_PER_PURCHASE_SA;
        }

        currentTotal -= cylinder_price;

        double newSideLength;
        if (upgradeForVolume) {
            newSideLength = Math.pow(currentTotal / Math.PI, 1.0/3.0);
        } else {
            double a = 2 * Math.PI;
            double b = 2 * Math.PI;
            double c = -currentTotal;
            newSideLength = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
        }

        cylinders++;
        cylinder_price = calculatePriceFromQuantity(INITIAL_PRICE, cylinders);

        return newSideLength;
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}