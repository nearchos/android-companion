package io.github.nearchos.lib;

public class Cylinder extends Circle {

    private final double height;

    public Cylinder(double radius, double height) {
        super(radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        // area = 2 * PI * r * h + 2 * circle base area
        final double baseArea = super.getArea();
        final double perimeter = 2 * Math.PI * getRadius();
        return perimeter * height + 2 * baseArea;
    }

    public double getVolume() {
        // volume = PI * r^2 * h
        return Math.PI * getRadius() * getRadius() * height;
    }
}
