package com.kkmoskalenko.oop;

final class Main {
    private static final String WORKERS_FILENAME = "workers.json";
    private static final int STORAGE_CAPACITY = 4;
    private static final int ORDERS_COUNT = 20;

    private Main() {

    }

    public static void main(final String[] args) {
        Pizzeria pizzeria = new Pizzeria(WORKERS_FILENAME, STORAGE_CAPACITY);
        pizzeria.start(ORDERS_COUNT);
    }
}
