package com.example.geometryclicker;

import java.lang.Math;
public class Formulas {
    public static enum ShapeType {
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
        PYRAMID

    }
    static class Area {

        public static double calculateArea(ShapeType shapeType, double... parameters) {
            switch (shapeType) {
                case CIRCLE:
                    return calculateCircleArea(parameters[0]);
                case RECTANGLE:
                    return calculateRectangleArea(parameters[0], parameters[1]);
                case TRIANGLE:
                    return calculateTriangleArea(parameters[0], parameters[1]);
                case SQUARE:
                    return calculateSquareArea(parameters[0]);
                default:
                    return -1;
            }
        }
        public static double calculateCircleArea(double radius) {
            return Math.PI * radius * radius;
        }

        public static double calculateRectangleArea(double base, double height) {
            return base * height;
        }

        public static double calculateTriangleArea(double base, double height) {
            return 0.5 * base * height;
        }

        public static double calculateSquareArea(double side) {
            return side * side;
        }
    }

    static class Perimeter {

        public static double calculatePerimeter(ShapeType shapeType, double... parameters) {
            switch (shapeType) {
                case CIRCLE:
                    return calculateCircleCircumference(parameters[0]);
                case RECTANGLE:
                    return calculateRectanglePerimeter(parameters[0], parameters[1]);
                case TRIANGLE:
                    return calculateTrianglePerimeter(parameters[0], parameters[1], parameters[2]);
                case SQUARE:
                    return calculateSquarePerimeter(parameters[0]);
                default:
                    return -1;
            }
        }
        public static double calculateCircleCircumference(double radius) {
            return 2 * Math.PI * radius;
        }

        public static double calculateRectanglePerimeter(double length, double width) {
            return 2 * (length + width);
        }

        public static double calculateTrianglePerimeter(double side1, double side2, double side3) {
            return side1 + side2 + side3;
        }

        public static double calculateSquarePerimeter(double side) {
            return 4 * side;
        }
    }

    static class Volume {

        public static double calculateVolume(ShapeType shapeType, double... parameters) {
            switch (shapeType) {
                case CUBE:
                    return calculateCubeVolume(parameters[0]);
                case RECTANGULAR_PRISM:
                    return calculateRectangularPrismVolume(parameters[0], parameters[1], parameters[2]);
                case SPHERE:
                    return calculateSphereVolume(parameters[0]);
                case CYLINDER:
                    return calculateCylinderVolume(parameters[0], parameters[1]);
                case CONE:
                    return calculateConeVolume(parameters[0], parameters[1]);
                case PRISM:
                    return calculatePrismVolume(parameters[0], parameters[1]);
                case PYRAMID:
                    return calculatePyramidVolume(parameters[0], parameters[1]);
                default:
                    return -1;
            }
        }
        public static double calculateCubeVolume(double side) {
            return Math.pow(side, 3);
        }

        public static double calculateRectangularPrismVolume(double length, double width, double height) {
            return length * width * height;
        }

        public static double calculateSphereVolume(double radius) {
            return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
        }

        public static double calculateCylinderVolume(double radius, double height) {
            return Math.PI * Math.pow(radius, 2) * height;
        }

        public static double calculateConeVolume(double radius, double height) {
            return calculateCylinderVolume(radius, height) / 3.0;
        }

        public static double calculatePrismVolume(double baseArea, double height) {
            return baseArea*height;
        }

        public static double calculatePyramidVolume(double baseArea, double height) {
            return (1.0 / 3.0) * baseArea * height;
        }

    }

    static class SurfaceArea {

        public static double calculateSurfaceArea(ShapeType shapeType, double... parameters) {
            switch (shapeType) {
                case CUBE:
                    return calculateCubeSurfaceArea(parameters[0]);
                case RECTANGULAR_PRISM:
                    return calculateRectangularPrismSurfaceArea(parameters[0], parameters[1], parameters[2]);
                case SPHERE:
                    return calculateSphereSurfaceArea(parameters[0]);
                case CYLINDER:
                    return calculateCylinderSurfaceArea(parameters[0], parameters[1]);
                case CONE:
                    return calculateConeSurfaceArea(parameters[0], parameters[1]);
                case PYRAMID:
                    return calculatePyramidSurfaceArea(parameters[0], parameters[1], parameters[2]);
                default:
                    return -1;
            }
        }
        public static double calculateCubeSurfaceArea(double side) {
            return 6 * Math.pow(side, 2);
        }

        public static double calculateRectangularPrismSurfaceArea(double length, double width, double height) {
            return 2 * (length * width + width * height + height * length);
        }

        public static double calculateSphereSurfaceArea(double radius) {
            return 4 * Math.PI * Math.pow(radius, 2);
        }

        public static double calculateCylinderSurfaceArea(double radius, double height) {
            return 2 * Math.PI * radius * (radius + height);
        }

        public static double calculateConeSurfaceArea(double radius, double slantHeight) {
            return Math.PI * radius * (radius + slantHeight);
        }
        public static double calculatePyramidSurfaceArea(double baseArea, double basePerimeter, double slantHeight) {
            return baseArea + (0.5 * basePerimeter * slantHeight);
        }
    }

}
