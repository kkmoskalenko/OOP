package com.kkmoskalenko.oop;

import static java.lang.Integer.min;

public class RecordBook {
    private final int studentID;

    private final Semester[] semesters;
    private int graduationWorkGrade = 0;

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

    public int getGraduationWorkGrade() {
        return graduationWorkGrade;
    }

    public void setGraduationWorkGrade(int grade) {
        Course.validateGrade(grade);
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
        return (excellentGradesPercent() >= 0.75) &&
                (minGrade() > 3) && (graduationWorkGrade == 5);
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
        int minGrade = 5;

        for (Semester sem : semesters) {
            if (sem == null) {
                continue;
            }

            minGrade = min(minGrade, sem.minGrade());
        }

        return minGrade;
    }
}
