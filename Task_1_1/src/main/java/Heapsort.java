public class Heapsort {
    public static void sort(int[] arr) {
        int n = arr.length;

        int k = 0;
        int[] heap = new int[n];

        for (int i = 0; i < n; i++) {
            // Add
            heap[k++] = arr[i];
            siftUp(heap, k - 1);
        }

        for (int i = 0; i < n; i++) {
            arr[i] = heap[0];

            // Extract min
            int tmp = heap[0];
            heap[0] = heap[k - 1];
            heap[k - 1] = tmp;

            k--;
            siftDown(heap, 0, k);
        }
    }

    private static void siftUp(int[] heap, int v) {
        if (v == 0) return;

        int father = (v - 1) / 2;
        if (heap[v] < heap[father]) {
            int tmp = heap[v];
            heap[v] = heap[father];
            heap[father] = tmp;

            siftUp(heap, father);
        }
    }

    private static void siftDown(int[] heap, int v, int n) {
        int l = 2 * v + 1;
        int r = 2 * v + 2;

        if (l < n && r < n) {
            if (heap[v] <= heap[l] && heap[v] <= heap[r]) return;

            if (heap[l] < heap[r]) {
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
        } else if (l < n && heap[v] > heap[l]) {
            int tmp = heap[v];
            heap[v] = heap[l];
            heap[l] = tmp;
        } else if (r < n && heap[v] > heap[r]) {
            int tmp = heap[v];
            heap[v] = heap[r];
            heap[r] = tmp;
        }
    }
}