package io.github.nearchos.hellojava;

public class Square implements Shape2D {

    private final double side;

    public Square(final double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public double getArea() {
        return side * side;
    }
}