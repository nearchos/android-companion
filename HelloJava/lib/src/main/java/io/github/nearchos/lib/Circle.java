package io.github.nearchos.lib;

public class Circle implements Shape2D {

    private final double radius;

    public Circle(final double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }
}