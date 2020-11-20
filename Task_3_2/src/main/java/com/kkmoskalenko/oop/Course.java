package com.kkmoskalenko.oop;

public class Course {
    private final String name;
    private Grade grade;

    public Course(String name) {
        this.name = name;
        this.grade = Grade.UNDEFINED;
    }

    public Course(String name, int grade) {
        this.name = name;
        this.grade = Grade.fromRawValue(grade);
    }

    public String getName() {
        return name;
    }

    public boolean hasGrade() {
        return grade != null && grade != Grade.UNDEFINED;
    }

    public Grade getGrade() {
        if (!hasGrade()) {
            throw new RuntimeException("The grade is not specified");
        }

        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}