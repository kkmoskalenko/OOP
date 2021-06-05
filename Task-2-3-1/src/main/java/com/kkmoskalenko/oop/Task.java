package com.kkmoskalenko.oop;

import lombok.Data;

@Data
class Task {
    private String name;
    private String path;
    private boolean shouldSkipTests = false;

    void name(String name) {
        this.name = name;
    }

    void path(String path) {
        this.path = path;
    }

    void skipTests() {
        shouldSkipTests = true;
    }
}
