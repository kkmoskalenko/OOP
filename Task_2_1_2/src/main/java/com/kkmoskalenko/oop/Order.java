package com.kkmoskalenko.oop;

class Order {
    private static int lastID = 1;

    private final int id;
    private final Pizza.Type pizzaType;

    Order(final Pizza.Type pizzaType) {
        this.id = lastID++;
        this.pizzaType = pizzaType;
    }

    int getId() {
        return id;
    }

    Pizza.Type getPizzaType() {
        return pizzaType;
    }
}
