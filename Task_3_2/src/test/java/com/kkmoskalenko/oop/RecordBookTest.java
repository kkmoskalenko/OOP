package com.kkmoskalenko.oop;

import junit.framework.TestCase;

public class RecordBookTest extends TestCase {
    private final RecordBook book;

    public RecordBookTest() {
        Semester firstSemester = new Semester();
        firstSemester.addCourse(new Course("Введение в алгебру и анализ", 5));
        firstSemester.addCourse(new Course("Введение в дискретную математику", 4));
        firstSemester.addCourse(new Course("Императивное программирование", 5));

        Semester secondSemester = new Semester();
        secondSemester.addCourse(new Course("Цифровые платформы", 5));
        secondSemester.addCourse(new Course("Декларативное программирование", 5));

        book = new RecordBook(190661, new Semester[]{firstSemester, secondSemester});
    }

    public void testRecordBook1() {
        assertEquals(4.8, book.allTimeAverageGrade());
    }

    public void testRecordBook2() {
        book.setGraduationWorkGrade(Grade.GOOD);
        assertFalse(book.appliesForRedDiploma());

        book.setGraduationWorkGrade(Grade.EXCELLENT);
        assertTrue(book.appliesForRedDiploma());
    }

    public void testRecordBook3() {
        Semester[] semesters = book.getSemesters();

        assertFalse(semesters[0].appliesForIncreasedStipend());
        assertTrue(semesters[1].appliesForIncreasedStipend());
    }
}