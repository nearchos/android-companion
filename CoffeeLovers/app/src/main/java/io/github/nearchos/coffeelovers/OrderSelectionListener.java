package io.github.nearchos.coffeelovers;

import io.github.nearchos.coffeelovers.model.Order;

public interface OrderSelectionListener {
    void onOrderSelected(final Order order);
}
