package com.kkmoskalenko.oop;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.junit.Assert;

public class StackTest extends TestCase {

    public void testIntStack() {
        Stack<Integer> stack = new Stack<>();
        int elem;

        stack.push(123);
        stack.push(456);

        assertEquals(2, stack.count());

        elem = stack.pop().orElseThrow(
                AssertionFailedError::new);
        assertEquals(456, elem);
        assertEquals(1, stack.count());

        stack.push(789);
        assertEquals(2, stack.count());

        elem = stack.pop().orElseThrow(
                AssertionFailedError::new);
        assertEquals(789, elem);

        elem = stack.pop().orElseThrow(
                AssertionFailedError::new);
        assertEquals(123, elem);

        assertEquals(0, stack.count());

        stack.pop();
        assertEquals(0, stack.count());
    }

    public void testStringStack() {
        Stack<String> stack = new Stack<>();
        String elem;

        stack.push("hello");
        stack.push("world");

        assertEquals(2, stack.count());

        elem = stack.pop().orElseThrow(
                AssertionFailedError::new);
        assertEquals("world", elem);
        assertEquals(1, stack.count());

        elem = stack.pop().orElseThrow(
                AssertionFailedError::new);
        assertEquals("hello", elem);
        assertEquals(0, stack.count());
    }

    public void testIterator() {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(9);
        stack.push(2);
        stack.push(1);
        stack.push(3);

        int[] array = new int[5];
        int[] res = {1, 9, 2, 1, 3};

        int i = 0;
        for (int num : stack) {
            array[i++] = num;
        }

        Assert.assertArrayEquals(res, array);
    }
}