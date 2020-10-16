import com.kkmoskalenko.oop.Substring;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class SubstringTest extends TestCase {
    private static String getResourcePath(String name) {
        ClassLoader loader = SubstringTest.class.getClassLoader();
        return loader.getResource(name).getPath();
    }

    public void testPie() {
        String path1 = getResourcePath("input1.txt");
        String path2 = getResourcePath("input2.txt");

        try {
            int[] indices1 = Substring.findIndices(path1, "пирог");
            int[] indices2 = Substring.findIndices(path2, "пирог");

            int[] res1 = {7};
            int[] res2 = {};

            Assert.assertArrayEquals(res1, indices1);
            Assert.assertArrayEquals(res2, indices2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testLorem() {
        try {
            String path = getResourcePath("lorem.txt");

            int[] indices = Substring.findIndices(path, "lorem");
            int[] res = {
                    1045, 1065, 1133, 3195, 5083, 5557, 7515, 7556, 9419, 9632, 9724, 9895, 11719, 12764, 13050, 14146,
                    15685, 15712, 16960, 17397, 20103, 20434, 21106, 23851, 24251, 24430, 24696, 24960, 25563, 26302,
                    26860, 27236, 27389, 29190, 30015, 31222, 33611, 35563, 35616, 41847, 42773, 43854, 43961, 47188,
                    47723, 47848, 48242, 48403, 52237, 52759, 55634, 56131, 56818, 57394, 57468, 57713, 58092, 59454,
                    61281, 61958, 63118, 63994, 64472, 68492, 69511, 74754, 74983, 76090, 81325, 81868, 83987, 84487,
                    84679, 86205, 89779, 90181, 91929, 92237, 92606, 93607, 94253, 95005, 98706, 98729, 99377
            };

            Assert.assertArrayEquals(res, indices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testRandom() {
        String pathname = "large-file.txt";

        File f = new File(pathname);
        if (!f.exists()) {
            generateRandomFile();
        }

        try {
            int[] indices = Substring.findIndices(
                    pathname, "SUBSTRING");

            double frequency = indices.length / 1000.0;
            assertEquals(5, Math.round(frequency));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateRandomFile() {
        int RANDOM_STRINGS_COUNT = 100000;
        int RANDOM_STRINGS_LENGTH = 100000;

        try {
            FileOutputStream outputFile = new FileOutputStream("large-file.txt");

            for (int i = 0; i < RANDOM_STRINGS_COUNT; i++) {
                outputFile.write(getAlphaNumericString(RANDOM_STRINGS_LENGTH).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAlphaNumericString(int n) {
        Random random = new Random();

        int number = random.nextInt(100);
        if (number >= 95) {
            return "SUBSTRING";
        }

        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (string.length() * Math.random());
            sb.append(string.charAt(index));
        }

        return sb.toString();
    }
}