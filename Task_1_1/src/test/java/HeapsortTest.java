import com.kkmoskalenko.oop.Heapsort;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;

public class HeapsortTest extends TestCase {

    public void testSort() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] res = {1, 2, 3, 4, 5};

        Heapsort.sort(arr);
        Assert.assertArrayEquals(arr, res);
    }

    public void testSortEmpty() {
        int[] arr = {};
        int[] res = {};

        Heapsort.sort(arr);
        Assert.assertArrayEquals(arr, res);
    }

    public void testSortNegative() {
        int[] arr = {5, -4, 3, -2, 1};
        int[] res = {-4, -2, 1, 3, 5};

        Heapsort.sort(arr);
        Assert.assertArrayEquals(arr, res);
    }

    public void testSortSorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] res = {1, 2, 3, 4, 5};

        Heapsort.sort(arr);
        Assert.assertArrayEquals(arr, res);
    }

    public void testSortRandom() {
        Random rand = new Random();

        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt();
        }

        int[] copy = arr.clone();

        Heapsort.sort(arr);
        Arrays.sort(copy);

        Assert.assertArrayEquals(arr, copy);
    }
}