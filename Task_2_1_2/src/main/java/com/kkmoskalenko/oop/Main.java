package com.kkmoskalenko.oop;

class Main {
    private static final int BAKERS_COUNT = 5;
    private static final int DELIVERYMEN_COUNT = 2;
    private static final int STORAGE_CAPACITY = 4;
    private static final int ORDERS_COUNT = 20;

    public static void main(final String[] args) {
        Pizzeria pizzeria = new Pizzeria(
                BAKERS_COUNT,
                DELIVERYMEN_COUNT,
                STORAGE_CAPACITY
        );
        pizzeria.start(ORDERS_COUNT);
    }
}
