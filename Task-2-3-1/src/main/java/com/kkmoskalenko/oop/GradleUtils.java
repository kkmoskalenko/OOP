package com.kkmoskalenko.oop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

final class GradleUtils {
    private static void execute(
            final File projectDir,
            final List<String> command,
            final StringCallback callback
    ) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.directory(projectDir);
        Process process = builder.start();

        InputStream in = process.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inReader);

        String line;
        while ((line = reader.readLine()) != null) {
            callback.call(line);
        }

        int exitCode = process.waitFor();
        assert exitCode == 0;
    }

    static boolean build(final File projectDir) {
        AtomicBoolean success = new AtomicBoolean(false);
        List<String> command = Arrays.asList(
                "./gradlew", "build", "-x", "test");

        try {
            execute(projectDir, command, (line) -> {
                if (line.startsWith("BUILD SUCCESSFUL")) {
                    success.set(true);
                }
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        return success.get();
    }

    static String test(final File projectDir) {
        List<String> command = Arrays.asList("./gradlew", "test");

        try {
            execute(projectDir, command, (str) -> {
                // Do nothing
            });
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        File reportsDir = new File(projectDir, "build/test-results/test");
        File[] reports = reportsDir.listFiles((dir, name) ->
                name.startsWith("TEST") && name.endsWith(".xml"));
        assert reports != null && reports.length > 0;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(reports[0]);
            Element el = doc.getDocumentElement();

            int total = Integer.parseInt(el.getAttribute("tests"));
            int failed = Integer.parseInt(el.getAttribute("failures"));

            return String.format("%d/%d", total - failed, total);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "TESTING FAILED";
    }

    private interface StringCallback {
        void call(final String str);
    }
}
