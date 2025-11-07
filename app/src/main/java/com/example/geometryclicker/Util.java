package com.example.geometryclicker;

import android.content.SharedPreferences;
import java.text.DecimalFormat;

public class Util {

    public static final int scaleFactor = 5000;

    public static void saveShapeData(SharedPreferences.Editor editor, String shapeName, int quantity, double price, double ups) {
        editor.putInt("number_of_" + shapeName.toLowerCase() + "s", quantity);
        editor.putString(shapeName.toLowerCase() + "_price", String.valueOf(price));
        editor.putString(shapeName.toLowerCase() + "_ups", String.valueOf(ups));
    }

    public static void loadShapeData(SharedPreferences prefs, String shapeName) {
        int quantity = prefs.getInt("number_of_" + shapeName.toLowerCase() + "s", 0);

        double price;
        double ups;

        String priceString = prefs.getString(shapeName.toLowerCase() + "_price", null);
        String upsString = prefs.getString(shapeName.toLowerCase() + "_ups", null);

        if (priceString != null && upsString != null) {
            price = Double.parseDouble(priceString);
            ups = Double.parseDouble(upsString);
        } else {
            price = prefs.getFloat(shapeName.toLowerCase() + "_price", 0);
            ups = prefs.getFloat(shapeName.toLowerCase() + "_ups", 0);
        }

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
            case "prism":
                Prism.prisms = quantity;
                Prism.prism_price = price;
                Prism.prism_UPS = ups;
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
        if (number < 1000) {
            if (isInteger(number)) {
                return Integer.toString((int) number);
            }
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