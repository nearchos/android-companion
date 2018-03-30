package io.github.nearchos.coffeelovers.model;

import java.io.Serializable;

/**
 * @author Nearchos
 * Created: 30-Mar-18
 */
public class Order implements Serializable {

    public enum Status { OPEN, CLOSED };

    private int id;
    private String name;
    private Coffee coffee;
    private int quantity;
    private Status status;
    // todo add timestamp

    private Order(int id, String name, Coffee coffee, int quantity, Status status) {
        this.id = id;
        this.name = name;
        this.coffee = coffee;
        this.quantity = quantity;
        this.status = status;
    }

    public Order(int id, String name, Coffee coffee, int quantity) {
        this(id, name, coffee, quantity, Status.OPEN);
    }

    public Order(int id, String name, Coffee.Type type, Coffee.Size size, Coffee.Milk milk, boolean caffeineFree, int quantity, Status status) {
        this(id, name, new Coffee(type, size, milk, caffeineFree), quantity, status);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public Status getStatus() {
        return status;
    }
}