package com.example.geometryclicker;

import android.content.SharedPreferences;
import java.lang.Math;
import java.text.DecimalFormat;

public class Util {

    public static void saveShapeData(SharedPreferences.Editor editor, String shapeName, int quantity, double price, double ups) {
        editor.putInt("number_of_" + shapeName.toLowerCase() + "s", quantity);
        editor.putFloat(shapeName.toLowerCase() + "_price", (float) price);
        editor.putFloat(shapeName.toLowerCase() + "_ups", (float) ups);
    }

    public static void loadShapeData(SharedPreferences prefs, String shapeName) {
        int quantity = prefs.getInt("number_of_" + shapeName.toLowerCase() + "s", 0);
        double price = prefs.getFloat(shapeName.toLowerCase() + "_price", 0);
        double ups = prefs.getFloat(shapeName.toLowerCase() + "_ups", 0);

        switch(shapeName.toLowerCase()) {
            case "cone":
                Cone.cones = quantity;
                Cone.cone_price = price;
                Cone.cone_UPS = ups;
                break;
            case "pyramid":
                Pyramid.pyramids = quantity;
                Pyramid.pyramid_price = price;
                Pyramid.pyramid_UPS = ups;
                break;
            case "cylinder":
                Cylinder.cylinders = quantity;
                Cylinder.cylinder_price = price;
                Cylinder.cylinder_UPS = ups;
                break;
        }
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##");

    public static boolean isInteger(double d) {
        return (d % 1) == 0;
    }

    public enum ShapeType {
        CIRCLE,
        RECTANGLE,
        TRIANGLE,
        SQUARE,
        CUBE,
        RECTANGULAR_PRISM,
        SPHERE,
        CYLINDER,
        CONE,
        PRISM,
        PYRAMID,
        PENTAGON,
        HEXAGON,
        HEPTAGON
    }

    public static String formatNumber(double number) {
        if (isInteger(number)) {
            return Integer.toString((int) number);
        }

        if (number < 1000) {
            return DECIMAL_FORMAT.format(number);
        }

        String[] suffixes = new String[]{"", "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", "Dc", "Ud", "Dd", "Td", "Qt", "Qi", "Sxt", "Spt", "Oct", "Nod", "Vg"};

        int digitLength = (int) Math.floor(Math.log10(number));

        int suffixIndex = (int) Math.floor(digitLength / 3);

        if (suffixIndex >= suffixes.length) {
            return DECIMAL_FORMAT.format(number);
        }

        double shortNumber = number / Math.pow(1000, suffixIndex);

        return DECIMAL_FORMAT.format(shortNumber) + suffixes[suffixIndex];
    }
}