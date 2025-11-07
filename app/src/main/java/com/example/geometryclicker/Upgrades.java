package com.example.geometryclicker;

public class Upgrades {
    public static final int VOLUME_UPGRADE_PRICE = 50000000;
    public static final int SIDE_LENGTH_UPGRADE_BASE_PRICE = 2000000;
    public static final int HEIGHT_UPGRADE_BASE_PRICE = 3000000;
    private static final double UPGRADE_PRICE_MULTIPLIER = 1.5;

    public static boolean volume = false;
    public static boolean boughtPyramid = false;
    public static boolean boughtCylinder = false;
    public static boolean boughtPrism = false;

    public static int sideLengthUpgradesPurchased = 0;
    public static int heightUpgradesPurchased = 0;

    public static String getFormattedVolumePrice() {
        return Util.formatNumber(VOLUME_UPGRADE_PRICE);
    }

    public static String getFormattedSideLengthPrice() {
        return Util.formatNumber(calculateSideLengthUpgradePrice());
    }

    public static String getFormattedHeightPrice() {
        return Util.formatNumber(calculateHeightUpgradePrice());
    }

    public static double calculateSideLengthUpgradePrice() {
        return Math.round(SIDE_LENGTH_UPGRADE_BASE_PRICE * Math.pow(UPGRADE_PRICE_MULTIPLIER, sideLengthUpgradesPurchased));
    }

    public static double calculateHeightUpgradePrice() {
        return Math.round(HEIGHT_UPGRADE_BASE_PRICE * Math.pow(UPGRADE_PRICE_MULTIPLIER, heightUpgradesPurchased));
    }
}