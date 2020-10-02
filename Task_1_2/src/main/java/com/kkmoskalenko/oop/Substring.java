package com.kkmoskalenko.oop;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Substring {
    public static int[] findIndices(String pathname, String substring) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(pathname), StandardCharsets.UTF_8
        ));

        int currentPos = 0;
        int bufferLength = substring.length();

        char[] buffer = new char[bufferLength];
        char[] prevBuffer = new char[bufferLength];

        List<Integer> indices = new ArrayList<>();

        while (reader.read(buffer) != -1) {
            String str = new String(prevBuffer) + new String(buffer);
            int index = str.indexOf(substring);
            if (index != -1) {
                index = index - bufferLength + currentPos;

                if (indices.isEmpty() || index != indices.get(indices.size() - 1)) {
                    indices.add(index);
                }
            }

            currentPos += bufferLength;
            prevBuffer = buffer.clone();
        }

        return indices.stream().mapToInt(i -> i).toArray();
    }
}
