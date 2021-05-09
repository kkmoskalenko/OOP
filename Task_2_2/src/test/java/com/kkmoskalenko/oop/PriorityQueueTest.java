package com.kkmoskalenko.oop;

import junit.framework.TestCase;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PriorityQueueTest extends TestCase {

    public void testExtractMin() {
        PriorityQueue<String, Integer> queue = new PriorityQueue<>();

        queue.insert("dog", 200);
        queue.insert("human", 10);

        assertEquals("human", queue.extractMin().value);

        queue.insert("penguin", 5);
        queue.insert("parrot", 500);

        assertEquals("penguin", queue.extractMin().value);
    }

    public void testStream() {
        PriorityQueue<Integer, Integer> queue = new PriorityQueue<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt();
            int priority = random.nextInt(500);

            queue.insert(value, priority);
        }

        List<PriorityQueue<Integer, Integer>.Element> filtered =
                StreamSupport.stream(queue.spliterator(), false)
                        .filter(a -> a.priority > 10 && a.priority < 100)
                        .collect(Collectors.toList());

        for (var element : filtered) {
            assertTrue(element.priority > 10 && element.priority < 100);
        }
    }

}