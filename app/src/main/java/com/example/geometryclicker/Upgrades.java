package com.example.geometryclicker;

public class Upgrades {
    public static final int VOLUME_UPGRADE_PRICE = 1000000;

    public static boolean volume = false;
    public static boolean boughtPyramid = false;
    public static boolean boughtCylinder = false;

    public static String getFormattedVolumePrice() {
        return Util.formatNumber(VOLUME_UPGRADE_PRICE);
    }
}