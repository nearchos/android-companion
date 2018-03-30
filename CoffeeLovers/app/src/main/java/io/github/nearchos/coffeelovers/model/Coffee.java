package io.github.nearchos.coffeelovers.model;

import java.io.Serializable;

/**
 * @author Nearchos
 * Created: 29-Mar-18
 */
public class Coffee implements Serializable {

    public enum Type { CAPPUCCINO, LATTE, AMERICANO };
    public enum Size { SMALL, MEDIUM, LARGE };
    public enum Milk { NO_MILK, WHOLE_MILK, SEMI_SKIMMED_MILK, SKIMMED_MILK, LACTOSE_FREE_MILK, SOYA_BASED_MILK };

    private Type type;
    private Size size;
    private Milk milk;
    private boolean caffeineFree;

    public Coffee(Type type, Size size, Milk milk, boolean caffeineFree) {
        this.type = type;
        this.size = size;
        this.milk = milk;
        this.caffeineFree = caffeineFree;
    }

    public Type getType() {
        return type;
    }

    public Size getSize() {
        return size;
    }

    public Milk getMilk() {
        return milk;
    }

    public boolean isCaffeineFree() {
        return caffeineFree;
    }

    @Override
    public String toString() {
        return size + " " + type + (caffeineFree ? " caffeine free, " : ", ") + " with " + milk;
    }
}