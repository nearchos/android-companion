package io.github.nearchos.lib;

/**
 * Abstracts a geometric circle with a set radius.
 */
public class Circle implements Shape2D {

    // a public constant of type double - by convention named with CAPITALS
    public static final double DEFAULT_RADIUS = 1d;

    // a private, member variable of type double
    private final double radius;

    /**
     * A default constructor. If not defined, a default empty constructor is
     * implicitly added by the compiler.
     */
    public Circle() {
        this(DEFAULT_RADIUS); // call the constructor with one double argument
    }

    /**
     * A custom constructor with one double argument to initialize radius
     * @param radius the value to initialize the member variable radius
     */
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