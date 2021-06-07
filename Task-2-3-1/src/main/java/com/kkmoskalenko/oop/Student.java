package com.kkmoskalenko.oop;

import lombok.Data;

import java.io.File;
import java.io.IOException;

@Data
class Student {
    private String name;
    private String repository;

    void name(final String name) {
        this.name = name;
    }

    void repo(final String repository) {
        this.repository = repository;
    }

    File cloneRepo() {
        String clonedRepos = System.getProperty("user.dir") + "/cloned-repos";
        File repoDirectory = new File(clonedRepos, name);

        try {
            GitUtils.clone(repository, repoDirectory);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to clone the repository");
        }

        return repoDirectory;
    }
}
