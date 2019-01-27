package io.github.nearchos.hellojava;

public abstract class AbstractShape2D implements Shape2D, Describable {

    @Override
    public String getDescription() {
        return getClass().getSimpleName() + " of area " + getArea();
    }
}