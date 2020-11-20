package com.kkmoskalenko.oop;

import static java.lang.Integer.min;

public class RecordBook {
    public final int studentID;

    public final int educationDuration;
    public final Semester[] semesters;
    private int graduationWorkGrade;

    public RecordBook(int studentID, int educationDuration) {
        if (educationDuration < 1) {
            throw new RuntimeException("The education duration must be positive.");
        }

        this.studentID = studentID;
        this.educationDuration = educationDuration;
        this.semesters = new Semester[educationDuration];
        graduationWorkGrade = 0;
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
