package com.example.geometryclicker;

public class Cylinder {
    private static final double CYLINDER_UPS_PER_PURCHASE_SA_RADIUS = calculateSurfaceArea(1, 1);
    private static final double CYLINDER_UPS_PER_PURCHASE_SA_HEIGHT = calculateSurfaceArea(1, 1);
    private static final double CYLINDER_UPS_PER_PURCHASE_VOLUME_RADIUS = calculateVolume(1, 1);
    private static final double CYLINDER_UPS_PER_PURCHASE_VOLUME_HEIGHT = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 2.2;
    public static final double INITIAL_PRICE = calculateSurfaceArea(1,1) * Util.scaleFactor * 200;
    public static final int UNLOCK_PRICE = 10000000;

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

    public static double[] buyCylinder(double radius, double height, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(radius, height);
            cylinder_UPS += CYLINDER_UPS_PER_PURCHASE_VOLUME_RADIUS + CYLINDER_UPS_PER_PURCHASE_VOLUME_HEIGHT;
        } else {
            currentTotal = calculateSurfaceArea(radius, height);
            cylinder_UPS += CYLINDER_UPS_PER_PURCHASE_SA_RADIUS + CYLINDER_UPS_PER_PURCHASE_SA_HEIGHT;
        }

        currentTotal -= cylinder_price;

        double newRadius = radius + 1;
        double newHeight = height + 1;

        if (upgradeForVolume) {
            double volume = currentTotal;
            double tempRadius = Math.pow(volume / (Math.PI * newHeight), 0.5);
            if (tempRadius > newRadius) {
                newRadius = tempRadius;
            }
        } else {
            double surfaceArea = currentTotal;
            double a = 2 * Math.PI;
            double b = 2 * Math.PI * newHeight;
            double c = -surfaceArea;
            double tempRadius = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            if (tempRadius > newRadius) {
                newRadius = tempRadius;
            }
        }

        cylinders++;
        cylinder_price = calculatePriceFromQuantity(INITIAL_PRICE, cylinders);

        return new double[]{newRadius, newHeight};
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}