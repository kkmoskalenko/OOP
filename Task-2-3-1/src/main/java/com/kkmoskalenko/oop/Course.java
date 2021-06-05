package com.kkmoskalenko.oop;

import groovy.lang.Closure;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Course {
    private final List<Group> groups = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();

    void group(String name, Closure<Void> closure) {
        Group group = new Group();
        group.setName(name);
        closure.setDelegate(group);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        groups.add(group);
    }

    void task(Closure<Void> closure) {
        Task task = new Task();
        closure.setDelegate(task);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        tasks.add(task);
    }
}
