package com.kkmoskalenko.oop;

import java.util.ArrayDeque;

class QueueContainer<T> {
    private final Integer capacity;
    private final ArrayDeque<T> queue;

    QueueContainer() {
        this.capacity = null;
        this.queue = new ArrayDeque<>();
    }

    QueueContainer(int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }

    synchronized void add(T object) {
        while (capacity != null && queue.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        queue.add(object);
        notify();
    }

    synchronized T get(boolean shouldExit) {
        while (queue.isEmpty()) {
            try {
                wait();
                if (shouldExit) {
                    return null;
                }
            } catch (InterruptedException ignored) {

            }
        }

        T res = queue.poll();
        notify();
        return res;
    }

    synchronized boolean isNotEmpty() {
        return !queue.isEmpty();
    }
}
