package com.example.geometryclicker;

public class Cone {
    private static final double CONE_UPS_PER_PURCHASE_SA_RADIUS = calculateSurfaceArea(1, 1);
    private static final double CONE_UPS_PER_PURCHASE_SA_HEIGHT = calculateSurfaceArea(1, 1);
    private static final double CONE_UPS_PER_PURCHASE_VOLUME_RADIUS = calculateVolume(1, 1);
    private static final double CONE_UPS_PER_PURCHASE_VOLUME_HEIGHT = calculateVolume(1, 1);
    private static final double BASE_PRICE_MULTIPLIER = 1.8;
    public static final double INITIAL_PRICE = calculateSurfaceArea(1, 1) * Util.scaleFactor * 50;

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

    public static double[] buyCone(double radius, double height, boolean upgradeForVolume) {
        double currentTotal;
        if (upgradeForVolume) {
            currentTotal = calculateVolume(radius, height);
            cone_UPS += CONE_UPS_PER_PURCHASE_VOLUME_RADIUS + CONE_UPS_PER_PURCHASE_VOLUME_HEIGHT;
        } else {
            currentTotal = calculateSurfaceArea(radius, height);
            cone_UPS += CONE_UPS_PER_PURCHASE_SA_RADIUS + CONE_UPS_PER_PURCHASE_SA_HEIGHT;
        }

        currentTotal -= cone_price;

        double newRadius = radius + 1;
        double newHeight = height + 1;

        if (upgradeForVolume) {
            double volume = currentTotal;
            double tempRadius = Math.pow((3.0 * volume) / (Math.PI * newHeight), 0.5);
            if (tempRadius > newRadius) {
                newRadius = tempRadius;
            }
        } else {
            double surfaceArea = currentTotal;
            double slantHeight = Math.sqrt(newRadius * newRadius + newHeight * newHeight);
            double tempRadius = (-1 + Math.sqrt(1 + 4 * surfaceArea / Math.PI)) / 2;
            if (tempRadius > newRadius) {
                newRadius = tempRadius;
            }
        }

        cones++;
        cone_price = calculatePriceFromQuantity(INITIAL_PRICE, cones);

        return new double[]{newRadius, newHeight};
    }

    public static double calculatePriceFromQuantity(double basePrice, int quantity) {
        return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER, quantity));
    }
}