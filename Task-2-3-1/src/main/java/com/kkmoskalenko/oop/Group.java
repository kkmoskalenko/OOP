package com.kkmoskalenko.oop;

import groovy.lang.Closure;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Group {
    private final List<Student> students = new ArrayList<>();
    private String name;

    void student(Closure<Void> closure) {
        Student student = new Student();
        closure.setDelegate(student);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();

        students.add(student);
    }
}
