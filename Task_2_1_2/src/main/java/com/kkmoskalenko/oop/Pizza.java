package com.kkmoskalenko.oop;

import java.util.Random;

class Pizza {
    private final Type type;
    private final Order order;

    Pizza(final Order order) {
        this.type = order.getPizzaType();
        this.order = order;
    }

    public Type getType() {
        return type;
    }

    public Order getOrder() {
        return order;
    }

    enum Type {
        Margherita, Pepperoni, Hawaiian, BBQ;

        private static final Type[] VALUES = values();
        private static final Random RANDOM = new Random();

        static Type random() {
            return VALUES[RANDOM.nextInt(VALUES.length)];
        }
    }
}
