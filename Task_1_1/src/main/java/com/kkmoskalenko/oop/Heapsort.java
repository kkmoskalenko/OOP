package com.kkmoskalenko.oop;

public class Heapsort {
    public static void sort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, i, n);
        }

        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;

            siftDown(arr, 0, i);
        }
    }

    private static void siftDown(int[] heap, int v, int n) {
        int l = 2 * v + 1;
        int r = 2 * v + 2;

        if (l < n && r < n) {
            if (heap[v] >= heap[l] && heap[v] >= heap[r]) {
                return;
            }

            if (heap[l] > heap[r]) {
                int tmp = heap[v];
                heap[v] = heap[l];
                heap[l] = tmp;

                siftDown(heap, l, n);
            } else {
                int tmp = heap[v];
                heap[v] = heap[r];
                heap[r] = tmp;

                siftDown(heap, r, n);
            }
        } else if (l < n && heap[v] < heap[l]) {
            int tmp = heap[v];
            heap[v] = heap[l];
            heap[l] = tmp;
        } else if (r < n && heap[v] < heap[r]) {
            int tmp = heap[v];
            heap[v] = heap[r];
            heap[r] = tmp;
        }
    }
}