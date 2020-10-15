package com.kkmoskalenko.oop;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class Stack<T> implements Iterable<T> {
    @SuppressWarnings("unchecked")
    private T[] stack = (T[]) new Object[0];

    private int count = 0;
    private int capacity = 0;

    public void push(T item) {
        if (count == capacity) {
            capacity = capacity * 2 + 1;
            stack = Arrays.copyOf(stack, capacity);
        }

        stack[count++] = item;
    }

    public Optional<T> pop() {
        if (count == 0) {
            return Optional.empty();
        }

        return Optional.of(stack[--count]);
    }

    public int count() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public T next() {
                return stack[index++];
            }
        };
    }
}