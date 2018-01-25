package io.github.nearchos.hellojava;

public class InterfacesMain {

    public static void main(String[] args) {
        Circle circle = new Circle(1.0);
        Square square = new Square(1.0);
    }

    public String getDescription(Circle circle) {
        return "Circle of area: " + circle.getArea();
    }

    public String getDescription(Square square) {
        return "Square of area: " + square.getArea();
    }

    public String getDescription(Shape2D shape2D) {
        return "Shape of area: " + shape2D.getArea();
    }
}