package com.kkmoskalenko.oop;

import java.util.Arrays;

public class RecordBook {
    private final int studentID;

    private final Semester[] semesters;
    private Grade graduationWorkGrade = Grade.UNDEFINED;

    public RecordBook(int studentID, Semester[] semesters) {
        this.studentID = studentID;
        this.semesters = semesters;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getEducationDuration() {
        return semesters.length;
    }

    public Semester[] getSemesters() {
        return semesters;
    }

    public Grade getGraduationWorkGrade() {
        return graduationWorkGrade;
    }

    public void setGraduationWorkGrade(Grade grade) {
        this.graduationWorkGrade = grade;
    }

    public double allTimeAverageGrade() {
        int count = 0;
        int sum = 0;

        for (Semester sem : semesters) {
            if (sem == null) {
                continue;
            }

            int semSum = sem.gradedCoursesSum();
            int semCount = sem.gradedCoursesCount();

            if (semCount > 0) {
                sum += semSum;
                count += semCount;
            }
        }

        return (double) sum / count;
    }

    public boolean appliesForRedDiploma() {
        return (excellentGradesPercent() >= 0.75) && (minGrade() > 3) && (
                graduationWorkGrade == Grade.UNDEFINED ||
                        graduationWorkGrade == Grade.EXCELLENT);
    }

    private double excellentGradesPercent() {
        int excGrades = 0;
        int totalGrades = 0;

        for (Semester sem : semesters) {
            if (sem == null) {
                continue;
            }

            int semExcGrades = sem.excellentGradedCoursesCount();
            int semTotalGrades = sem.gradedCoursesCount();

            if (semTotalGrades > 0) {
                excGrades += semExcGrades;
                totalGrades += semTotalGrades;
            }
        }

        return (double) excGrades / totalGrades;
    }

    private int minGrade() {
        return Arrays.stream(semesters).mapToInt(
                Semester::minGrade).min().orElse(5);
    }
}
