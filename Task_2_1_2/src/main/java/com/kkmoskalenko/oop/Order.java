package com.kkmoskalenko.oop;

class Order {
    private static int lastID = 1;

    private final int id;
    private final Pizza.Type pizzaType;
    private final long creationTime;

    Order(final Pizza.Type pizzaType) {
        this.id = lastID++;
        this.pizzaType = pizzaType;
        this.creationTime = System.currentTimeMillis();
    }

    int getId() {
        return id;
    }

    Pizza.Type getPizzaType() {
        return pizzaType;
    }

    long getElapsedTime() {
        long now = System.currentTimeMillis();
        return now - creationTime;
    }
}
