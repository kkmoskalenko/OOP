package com.kkmoskalenko.oop;

import java.io.File;
import java.io.IOException;

final class GitUtils {
    private GitUtils() {

    }

    private static boolean deleteDirectory(
            final File directoryToBeDeleted
    ) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    static void clone(
            final String repositoryLink,
            final File cloneDirectory
    ) throws IOException, InterruptedException {
        if (cloneDirectory.exists()) {
            assert cloneDirectory.isDirectory();
            assert deleteDirectory(cloneDirectory);
        }

        Process process = Runtime.getRuntime()
                .exec(new String[]{
                        "git", "clone", repositoryLink,
                        cloneDirectory.getAbsolutePath()
                });

        int exitCode = process.waitFor();
        assert exitCode == 0;
    }
}
