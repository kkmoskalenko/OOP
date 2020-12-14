package com.kkmoskalenko.oop;

import java.util.*;
import java.util.stream.Collectors;

import static com.kkmoskalenko.oop.Vertex.Color.*;

public class OrderedSet<Element> {
    private final Map<Element, Integer> elementToVertexId = new HashMap<>();
    private final List<Vertex<Element>> vertices = new ArrayList<>();
    private int time;

    public OrderedSet(Element[] elements) {
        for (Element elem : elements) {
            Vertex<Element> vertex = new Vertex<>(elem);

            vertices.add(vertex);
            elementToVertexId.put(elem, vertices.size() - 1);
        }
    }

    public void addGreaterRelation(Element lhs, Element rhs) {
        int lhsId = elementToVertexId.get(lhs);
        int rhsId = elementToVertexId.get(rhs);

        Vertex<Element> firstVertex = vertices.get(lhsId);
        firstVertex.addRelation(rhsId);

        if (dfs()) {
            throw new IllegalStateException(
                    "This relation violates ths transitivity"
            );
        }
    }

    public List<Element> findMax() {
        return vertices.stream()
                .filter(vert -> vert.getParent() == -1)
                .map(Vertex::getValue)
                .collect(Collectors.toList());
    }

    public List<Element> topologicalSort() {
        int verticesCount = vertices.size();
        List<Element> sortedElements = new ArrayList<>(
                Collections.nCopies(verticesCount, null));

        for (Vertex<Element> vert : vertices) {
            int index = verticesCount - vert.getTime() - 1;
            sortedElements.set(index, vert.getValue());
        }

        return sortedElements;
    }

    private boolean dfs() {
        time = 0;

        for (Vertex<Element> vert : vertices) {
            vert.setColor(WHITE);
            vert.setParent(-1);
        }

        for (Vertex<Element> vert : vertices) {
            if (vert.getColor() == WHITE) {
                if (dfsHelper(vertices.indexOf(vert), -1)) {
                    // Returns true if there was a cycle
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfsHelper(int vertex, int parent) {
        Vertex<Element> current = vertices.get(vertex);
        current.setColor(GRAY);
        current.setParent(parent);

        for (int childId : current.getRelations()) {
            Vertex<Element> childVertex = vertices.get(childId);

            if (childVertex.getColor() == BLACK) {
                childVertex.setParent(vertex);
            }

            // Returns true if there was a cycle
            if (childVertex.getColor() == GRAY) {
                return true;
            }

            if (childVertex.getColor() == WHITE
                    && dfsHelper(childId, vertex)
            ) {
                return true;
            }
        }

        current.setColor(BLACK);
        current.setTime(time++);

        return false;
    }
}
