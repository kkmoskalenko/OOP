package com.kkmoskalenko.oop;

import java.util.ArrayDeque;

class QueueContainer<T> {
    private final Integer capacity;
    private final ArrayDeque<T> queue;

    private int blockedAdds = 0;
    private int blockedGets = 0;

    QueueContainer() {
        this.capacity = null;
        this.queue = new ArrayDeque<>();
    }

    QueueContainer(final int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }

    synchronized void add(final T object) {
        while (capacity != null && queue.size() >= capacity) {
            try {
                blockedAdds++;
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        queue.add(object);
        notify();
    }

    synchronized T get(final boolean shouldExit) {
        while (queue.isEmpty()) {
            try {
                blockedGets++;
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

    int getBlockedAdds() {
        return blockedAdds;
    }

    int getBlockedGets() {
        return blockedGets;
    }
}
