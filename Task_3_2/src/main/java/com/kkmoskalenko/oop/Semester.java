package com.kkmoskalenko.oop;

import java.util.ArrayList;

import static java.lang.Integer.min;

public class Semester {
    private final ArrayList<Course> courses = new ArrayList<>();

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public int gradedCoursesCount() {
        int count = 0;
        for (Course course : courses) {
            if (course.hasGrade()) {
                count++;
            }
        }

        return count;
    }

    public int excellentGradedCoursesCount() {
        int count = 0;
        for (Course course : courses) {
            if (course.hasGrade() && course.getGrade() == 5) {
                count++;
            }
        }

        return count;
    }

    public int gradedCoursesSum() {
        int sum = 0;
        for (Course course : courses) {
            if (course.hasGrade()) {
                sum += course.getGrade();
            }
        }

        return sum;
    }

    public int minGrade() {
        int minGrade = 5;
        for (Course course : courses) {
            if (course.hasGrade()) {
                int grade = course.getGrade();
                minGrade = min(grade, minGrade);
            }
        }

        return minGrade;
    }

    public boolean appliesForIncreasedStipend() {
        double average = (double) gradedCoursesSum() / gradedCoursesCount();
        return average == 5.0;
    }
}
