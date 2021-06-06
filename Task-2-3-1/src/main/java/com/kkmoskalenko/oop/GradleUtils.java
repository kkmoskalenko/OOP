package com.kkmoskalenko.oop;

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

    private interface StringCallback {
        void call(final String str);
    }
}
