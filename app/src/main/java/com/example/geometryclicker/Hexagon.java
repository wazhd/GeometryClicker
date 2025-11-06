//package com.example.geometryclicker;
//
//public class Hexagon {
//    private static final double HEXAGON_UPS_PER_PURCHASE = 4;
//    private static final double BASE_PRICE_MULTIPLIER_PERIMETER = 1.09;
//    private static final double BASE_PRICE_MULTIPLIER_AREA = 1.04;
//    public static final int INITIAL_PRICE = 1500;
//    public static final int UNLOCK_PRICE = 1800;
//
//    public static int hexagons = 0;
//    public static double hexagon_UPS = 0;
//    public static double hexagon_price = INITIAL_PRICE;
//
//    public static double buyHexagonUpgrade(double sideLength, boolean isArea) {
//        double currentTotal;
//        if (isArea) {
//            currentTotal = calculateArea(sideLength);
//        } else {
//            currentTotal = calculatePerimeter(sideLength);
//        }
//
//        currentTotal -= UNLOCK_PRICE;
//
//        double newSideLength;
//        if (isArea) {
//            newSideLength = Math.sqrt(currentTotal / ((3.0 * Math.sqrt(3)) / 2.0));
//        } else {
//            newSideLength = currentTotal / 6.0;
//        }
//
//        Upgrades.boughtHexagon = true;
//        return newSideLength;
//    }
//
//    public static double calculatePerimeter(double side) {
//        return 6 * side;
//    }
//
//    public static double calculateArea(double side) {
//        return ((3.0 * Math.sqrt(3)) / 2.0) * side * side;
//    }
//
//    public static double buyHexagon(double sideLength, boolean isArea) {
//        double currentTotal;
//        if (isArea) {
//            currentTotal = calculateArea(sideLength);
//        } else {
//            currentTotal = calculatePerimeter(sideLength);
//        }
//
//        currentTotal -= hexagon_price;
//
//        double newSideLength;
//        if (isArea) {
//            newSideLength = Math.sqrt(currentTotal / ((3.0 * Math.sqrt(3)) / 2.0));
//        } else {
//            newSideLength = currentTotal / 6.0;
//        }
//        hexagons++;
//        hexagon_UPS += HEXAGON_UPS_PER_PURCHASE;
//        hexagon_price = calculatePriceFromQuantity(INITIAL_PRICE, hexagons, isArea);
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