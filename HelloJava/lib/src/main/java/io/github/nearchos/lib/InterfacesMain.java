package io.github.nearchos.lib;

public class InterfacesMain {

    public static void main(String[] args) {
        Square square = new Square(1.0);
        System.out.println("square"
                + " with area " + square.getArea()
                + " and width " + square.getWidth()
                + " and height " + square.getHeight());
    }

    public String getDescription(Circle circle) {
        return "Circle of area: " + circle.getArea();
    }

    public String getDescription(Square square) {
        return "Square of area: " + square.getArea();
    }

    public String getDescription(Rectangle rectangle) {
        return "Rectangle of area: " + rectangle.getArea();
    }

    public String getDescription(Shape2D shape2D) {
        return "Shape of area: " + shape2D.getArea();
    }

}