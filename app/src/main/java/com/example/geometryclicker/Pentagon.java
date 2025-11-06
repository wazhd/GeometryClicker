//package com.example.geometryclicker;
//
//public class Pentagon {
//    private static final double PENTAGON_UPS_PER_PURCHASE = 2;
//    private static final double BASE_PRICE_MULTIPLIER_PERIMETER = 1.08;
//    private static final double BASE_PRICE_MULTIPLIER_AREA = 1.03;
//    public static final int INITIAL_PRICE = 1000;
//    public static final int UPGRADE_PRICE = 1500;
//
//    public static int pentagons = 0;
//    public static double pentagon_UPS = 0;
//    public static double pentagon_price = INITIAL_PRICE;
//
//    public static double calculatePerimeter(double side) {
//        return 5 * side;
//    }
//
//    public static double calculateArea(double side) {
//        return (5.0 / 4.0) * side * side * (1.0 / Math.tan(Math.PI / 5));
//    }
//
//    public static double buyPentagon(double sideLength, boolean isArea) {
//        double currentTotal;
//        if (isArea) {
//            currentTotal = calculateArea(sideLength);
//        } else {
//            currentTotal = calculatePerimeter(sideLength);
//        }
//
//        currentTotal -= pentagon_price;
//
//        double newSideLength;
//        if (isArea) {
//            newSideLength = Math.sqrt(currentTotal / ((5.0 / 4.0) * (1.0 / Math.tan(Math.PI / 5))));
//        } else {
//            newSideLength = currentTotal / 5.0;
//        }
//        pentagons++;
//        pentagon_UPS += PENTAGON_UPS_PER_PURCHASE;
//        pentagon_price = calculatePriceFromQuantity(INITIAL_PRICE, pentagons, isArea);
//
//        return newSideLength;
//    }
//
//    public static double calculatePriceFromQuantity(double basePrice, int quantity, boolean isArea) {
//        if (isArea) {
//            return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER_AREA, quantity));
//        } else {
//            return Math.round(basePrice * Math.pow(BASE_PRICE_MULTIPLIER_PERIMETER, quantity));
//        }
//    }
//}