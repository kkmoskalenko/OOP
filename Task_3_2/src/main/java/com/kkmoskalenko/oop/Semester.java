package com.kkmoskalenko.oop;

import java.util.ArrayList;

public class Semester {
    private final ArrayList<Course> courses = new ArrayList<>();

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public int gradedCoursesCount() {
        return (int) courses.stream().filter(Course::hasGrade).count();
    }

    public int excellentGradedCoursesCount() {
        return (int) courses.stream().filter(
                course -> course.hasGrade() &&
                        course.getGrade() == Grade.EXCELLENT
        ).count();
    }

    public int gradedCoursesSum() {
        return courses.stream().filter(Course::hasGrade).mapToInt(
                course -> course.getGrade().ordinal()).sum();
    }

    public int minGrade() {
        return courses.stream().filter(Course::hasGrade).mapToInt(
                course -> course.getGrade().ordinal()).min().orElse(5);
    }

    public boolean appliesForIncreasedStipend() {
        double average = (double) gradedCoursesSum() / gradedCoursesCount();
        return average == 5.0;
    }
}
