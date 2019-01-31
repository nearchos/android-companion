package io.github.nearchos.hellojava;

public class Line extends AbstractShape2D {

    private double length;

    public Line(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    @Override
    public double getArea() {
        return 0d;
    }
}