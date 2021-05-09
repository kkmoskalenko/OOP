package com.kkmoskalenko.oop;

import java.util.*;
import java.util.function.Consumer;

public class PriorityQueue<T, P> implements Iterable<PriorityQueue<T, P>.Element> {
    private static final int DEFAULT_CAPACITY = 10;

    final ArrayList<Element> heap;
    final Comparator<P> comparator;

    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        heap = new ArrayList<>(DEFAULT_CAPACITY);
        comparator = (a, b) -> ((Comparable<P>) a).compareTo(b);
    }

    public void insert(T e, P priority) {
        int idx = heap.size();
        heap.add(new Element(e, priority, idx));
        siftUp(idx);
    }

    public Element extractMin() {
        if (heap.size() == 0) {
            throw new IllegalStateException("PriorityQueue is empty");
        }

        if (heap.size() == 1) {
            return heap.remove(0);
        } else {
            Element head = heap.get(0);
            Element nodeToMoveToHead = heap.remove(heap.size() - 1);
            nodeToMoveToHead.idx = 0;
            heap.set(0, nodeToMoveToHead);
            siftDown(0);
            return head;
        }

    }

    private void siftDown(int i) {
        while (leftChild(i) < heap.size() && compare(heap.get(leftChild(i)).priority, heap.get(i).priority) < 0 ||
                rightChild(i) < heap.size() && compare(heap.get(rightChild(i)).priority, heap.get(i).priority) < 0) {
            int leftChildIdx = leftChild(i);
            int rightChildIdx = rightChild(i);
            if (rightChildIdx >= heap.size() || compare(heap.get(leftChildIdx).priority, heap.get(rightChildIdx).priority) < 0) {
                swap(i, leftChildIdx);
                i = leftChildIdx;
            } else {
                swap(i, rightChildIdx);
                i = rightChildIdx;
            }
        }
    }

    private void siftUp(int i) {
        while (i != 0 && compare(heap.get(parent(i)).priority, heap.get(i).priority) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private void swap(int idx1, int idx2) {
        Element node1 = heap.get(idx1);
        Element node2 = heap.get(idx2);

        node1.idx = idx2;
        node2.idx = idx1;

        heap.set(idx1, node2);
        heap.set(idx2, node1);
    }

    private int compare(P a, P b) {
        return comparator.compare(a, b);
    }

    public class Element {
        public Element(T value, P priority, int idx) {
            this.value = value;
            this.priority = priority;
            this.idx = idx;
        }

        T value;
        P priority;
        int idx;

        @Override
        public String toString() {
            return "Element{" +
                    "value=" + value +
                    ", priority=" + priority +
                    '}';
        }
    }

    @Override
    public Iterator<Element> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < heap.size();
            }

            @Override
            public Element next() {
                if (index < heap.size()) {
                    return heap.get(index++);
                }

                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public Spliterator<Element> spliterator() {
        return new PriorityQueueSpliterator(0, -1);
    }

    final class PriorityQueueSpliterator implements Spliterator<Element> {
        private int index;            // current index, modified on advance/split
        private int fence;            // one past the greatest index, -1 until first use

        PriorityQueueSpliterator(int origin, int fence) {
            this.index = origin;
            this.fence = fence;
        }

        private int getFence() {
            int hi = fence;
            if (hi < 0) {
                // initialize fence to size on first use
                hi = fence = heap.size();
            }
            return hi;
        }

        public PriorityQueueSpliterator trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null :
                    new PriorityQueueSpliterator(lo, index = mid);
        }

        public boolean tryAdvance(Consumer<? super Element> action) {
            if (action == null) {
                throw new NullPointerException();
            }

            if (fence < 0) {
                fence = heap.size();
            }

            int i = index;
            if (i < fence) {
                index = i + 1;
                action.accept(heap.get(i));
                return true;
            }

            return false;
        }

        public long estimateSize() {
            return getFence() - index;
        }

        public int characteristics() {
            return Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL;
        }
    }
}