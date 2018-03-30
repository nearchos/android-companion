package io.github.nearchos.testing.model;

import java.io.Serializable;

/**
 * @author Nearchos
 * Created: 29-Mar-18
 */
public class Cappuccino implements Serializable {

    private String size;
    private boolean hasCinnamon;
    private String milk;

    public Cappuccino(String size, boolean hasCinnamon, String milk) {
        this.size = size;
        this.hasCinnamon = hasCinnamon;
        this.milk = milk;
    }

    public String getSize() {
        return size;
    }

    public boolean isHasCinnamon() {
        return hasCinnamon;
    }

    public String getMilk() {
        return milk;
    }
}
