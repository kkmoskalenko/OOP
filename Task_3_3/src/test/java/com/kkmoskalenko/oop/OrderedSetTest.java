package com.kkmoskalenko.oop;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class OrderedSetTest extends TestCase {
    @Test
    public void testHaveCycles() {
        OrderedSet<String> set = new OrderedSet<>(new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7"
        });

        set.addGreaterRelation("0", "1");
        set.addGreaterRelation("0", "4");
        set.addGreaterRelation("1", "3");
        set.addGreaterRelation("1", "5");
        set.addGreaterRelation("2", "5");
        set.addGreaterRelation("5", "6");
        set.addGreaterRelation("6", "7");

        assertThrows(IllegalStateException.class, () ->
                set.addGreaterRelation("7", "5"));

    }

    @Test
    public void testTopologicalSort() {
        OrderedSet<String> set = new OrderedSet<>(new String[]{"A", "B", "C"});
        set.addGreaterRelation("A", "B");
        set.addGreaterRelation("A", "C");
        set.addGreaterRelation("C", "B");

        assertEquals(Arrays.asList("A", "C", "B"), set.topologicalSort());
    }

    @Test
    public void testFindMax() {
        OrderedSet<String> set = new OrderedSet<>(new String[]{
                "Мария", "Василий", "Вероника", "Татьяна", "Дмитрий"
        });

        set.addGreaterRelation("Мария", "Василий");
        set.addGreaterRelation("Вероника", "Василий");
        set.addGreaterRelation("Василий", "Татьяна");

        assertEquals(Arrays.asList("Мария", "Вероника", "Дмитрий"), set.findMax());
    }
}