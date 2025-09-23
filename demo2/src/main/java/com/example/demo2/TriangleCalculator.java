package com.example.demo2;

/**
 * Класс для вычислений, связанных с треугольником
 * Содержит логику расчета расстояния между точками, периметра и площади треугольника
 */
public class TriangleCalculator {

    /**
     * Вычисляет расстояние между двумя точками на плоскости
     * @param x1 координата X первой точки
     * @param y1 координата Y первой точки
     * @param x2 координата X второй точки
     * @param y2 координата Y второй точки
     * @return расстояние между точками
     */
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Вычисляет периметр треугольника по координатам трех вершин
     * @param x1 координата X первой вершины
     * @param y1 координата Y первой вершины
     * @param x2 координата X второй вершины
     * @param y2 координата Y второй вершины
     * @param x3 координата X третьей вершины
     * @param y3 координата Y третьей вершины
     * @return периметр треугольника
     */
    public static double calculatePerimeter(double x1, double y1, double x2, double y2, double x3, double y3) {
        double sideAB = calculateDistance(x1, y1, x2, y2);
        double sideBC = calculateDistance(x2, y2, x3, y3);
        double sideCA = calculateDistance(x3, y3, x1, y1);

        return sideAB + sideBC + sideCA;
    }

    /**
     * Вычисляет площадь треугольника по координатам трех вершин using Heron's formula
     * @param x1 координата X первой вершины
     * @param y1 координата Y первой вершины
     * @param x2 координата X второй вершины
     * @param y2 координата Y второй вершины
     * @param x3 координата X третьей вершины
     * @param y3 координата Y третьей вершины
     * @return площадь треугольника
     */
    public static double calculateArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        double sideAB = calculateDistance(x1, y1, x2, y2);
        double sideBC = calculateDistance(x2, y2, x3, y3);
        double sideCA = calculateDistance(x3, y3, x1, y1);

        // Используем формулу Герона
        double semiPerimeter = (sideAB + sideBC + sideCA) / 2;
        return Math.sqrt(semiPerimeter *
                (semiPerimeter - sideAB) *
                (semiPerimeter - sideBC) *
                (semiPerimeter - sideCA));
    }

    /**
     * Проверяет, образуют ли три точки треугольник (не лежат на одной прямой)
     * @param x1 координата X первой вершины
     * @param y1 координата Y первой вершины
     * @param x2 координата X второй вершины
     * @param y2 координата Y второй вершины
     * @param x3 координата X третьей вершины
     * @param y3 координата Y третьей вершины
     * @return true если точки образуют треугольник, false если лежат на одной прямой
     */
    public static boolean isTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        // Проверка через площадь треугольника (если площадь ≈ 0, точки на одной прямой)
        double area = calculateArea(x1, y1, x2, y2, x3, y3);
        return Math.abs(area) > 1e-10; // учитываем погрешность вычислений с плавающей точкой
    }
}