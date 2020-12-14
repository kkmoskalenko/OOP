package com.kkmoskalenko.oop;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vertex<T> {
    private final T value;
    private final List<Integer> relations = new ArrayList<>();

    private Color color;
    private int time;
    private int parent;

    public void addRelation(int id) {
        relations.add(id);
    }

    enum Color {
        WHITE, GRAY, BLACK
    }
}
