package com.example.geometryclicker;

public class Cone {
    private static final double CONE_UPS_PER_PURCHASE_SA = calculateSurfaceArea(1, 1);
    private static final double CONE_UPS_PER_PURCHASE_VOLUME = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 1.3;
    public static final int INITIAL_PRICE = 50000;

    public static int cones = 0;
    public static double cone_UPS = 0;
    public static double cone_price = INITIAL_PRICE;

    public static String getFormattedPrice() {
        return Util.formatNumber(cone_price);
    }

    public static double calculateSurfaceArea(double radius, double height) {
        double slantHeight = Math.sqrt(radius * radius + height * height);
        return Math.PI * radius * (radius + slantHeight);
    }

    public static double calculateVolume(double radius, double height) {
        return (1.0/3.0) * Math.PI * radius * radius * height;
    }

    public static double buyCone(double sideLength, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(sideLength, sideLength);
            cone_UPS += CONE_UPS_PER_PURCHASE_VOLUME;
        } else {
            currentTotal = calculateSurfaceArea(sideLength, sideLength);
            cone_UPS += CONE_UPS_PER_PURCHASE_SA;
        }

        currentTotal -= cone_price;

        double newSideLength;
        if (upgradeForVolume) {
            newSideLength = Math.pow((3.0 * currentTotal) / Math.PI, 1.0/3.0);
        } else {
            newSideLength = Math.sqrt(currentTotal / (Math.PI * (1 + Math.sqrt(2))));
        }

        cones++;
        cone_price = calculatePriceFromQuantity(INITIAL_PRICE, cones);

        return newSideLength;
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}