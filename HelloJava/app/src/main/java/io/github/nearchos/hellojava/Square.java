package io.github.nearchos.hellojava;

public class Square extends Rectangle {

    public Square(final double side) {
        super(side, side);
    }

    public double getSide() {
        return getWidth();
    }

    @Override
    public double getArea() {
        return Math.pow(getSide(), 2d);
    }
}