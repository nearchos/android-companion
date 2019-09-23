package io.github.nearchos.lib;

public class Triangle implements Shape2D, Describable {

    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getArea() {
        return Math.sqrt(-Math.pow(a, 4) + 2 * Math.pow(a*b, 2) + 2 * Math.pow(a*c, 2)
                - Math.pow(b, 4) + 2 * Math.pow(b*c, 2) - Math.pow(c, 4)) / 4.0;
    }

    @Override
    public String getDescription() {
        return "Triangle with sides a=" + a + ", b=" + b + " and c=" + c;
    }
}