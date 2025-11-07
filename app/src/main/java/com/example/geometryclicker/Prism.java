package com.example.geometryclicker;

public class Prism {
    private static final double PRISM_UPS_PER_PURCHASE_SA_SIDE = calculateSurfaceArea(1, 1);
    private static final double PRISM_UPS_PER_PURCHASE_SA_HEIGHT = calculateSurfaceArea(1, 1);
    private static final double PRISM_UPS_PER_PURCHASE_VOLUME_SIDE = calculateVolume(1, 1);
    private static final double PRISM_UPS_PER_PURCHASE_VOLUME_HEIGHT = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 2.5;
    public static final double INITIAL_PRICE = calculateSurfaceArea(1,1) * Util.scaleFactor * 400;
    public static final int UNLOCK_PRICE = 25000000;

    public static int prisms = 0;
    public static double prism_UPS = 0;
    public static double prism_price = INITIAL_PRICE;

    public static String getFormattedPrice() {
        return Util.formatNumber(prism_price);
    }

    public static String getFormattedUnlockPrice() {
        return Util.formatNumber(UNLOCK_PRICE);
    }

    public static double calculateSurfaceArea(double sideLength, double height) {
        return 2 * sideLength * sideLength + 4 * sideLength * height;
    }

    public static double calculateVolume(double sideLength, double height) {
        return sideLength * sideLength * height;
    }

    public static double[] buyPrism(double sideLength, double height, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(sideLength, height);
            prism_UPS += PRISM_UPS_PER_PURCHASE_VOLUME_SIDE + PRISM_UPS_PER_PURCHASE_VOLUME_HEIGHT;
        } else {
            currentTotal = calculateSurfaceArea(sideLength, height);
            prism_UPS += PRISM_UPS_PER_PURCHASE_SA_SIDE + PRISM_UPS_PER_PURCHASE_SA_HEIGHT;
        }

        currentTotal -= prism_price;

        double newSideLength = sideLength + 1;
        double newHeight = height + 1;

        if (upgradeForVolume) {
            double volume = currentTotal;
            double tempSide = Math.pow(volume / newHeight, 0.5);
            if (tempSide > newSideLength) {
                newSideLength = tempSide;
            }
        } else {
            double surfaceArea = currentTotal;
            double a = 2.0;
            double b = 4.0 * newHeight;
            double c = -surfaceArea;
            double tempSide = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            if (tempSide > newSideLength) {
                newSideLength = tempSide;
            }
        }

        prisms++;
        prism_price = calculatePriceFromQuantity(INITIAL_PRICE, prisms);

        return new double[]{newSideLength, newHeight};
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}