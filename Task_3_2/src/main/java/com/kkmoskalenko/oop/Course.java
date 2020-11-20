package com.kkmoskalenko.oop;

public class Course {
    private final String name;
    private Integer grade;

    public Course(String name) {
        this.name = name;
        this.grade = null;
    }

    public Course(String name, int grade) {
        validateGrade(grade);

        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public boolean hasGrade() {
        return grade != null;
    }

    public int getGrade() {
        if (!hasGrade()) {
            throw new RuntimeException("The grade is not specified");
        }

        return grade;
    }

    public void setGrade(int grade) {
        validateGrade(grade);
        this.grade = grade;
    }

    public static void validateGrade(int grade) {
        if (grade < 1 || grade > 5) {
            throw new RuntimeException("Invalid grade");
        }
    }
}