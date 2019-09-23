package io.github.nearchos.lib;

public class Line extends AbstractShape2D {

    public static final double AREA = 0d;

    private double length;

    public Line(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    @Override
    public double getArea() {
        return AREA;
    }
}