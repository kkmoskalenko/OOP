package com.kkmoskalenko.oop;

import junit.framework.TestCase;

public class RecordBookTest extends TestCase {
    private final RecordBook book = new RecordBook(190661, 2);

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Semester firstSemester = new Semester();
        firstSemester.courses.add(new Course("Введение в алгебру и анализ", 5));
        firstSemester.courses.add(new Course("Введение в дискретную математику", 4));
        firstSemester.courses.add(new Course("Императивное программирование", 5));

        Semester secondSemester = new Semester();
        secondSemester.courses.add(new Course("Цифровые платформы", 5));
        secondSemester.courses.add(new Course("Декларативное программирование", 5));

        book.semesters[0] = firstSemester;
        book.semesters[1] = secondSemester;
    }

    public void testRecordBook1() {
        assertEquals(4.8, book.allTimeAverageGrade());
    }

    public void testRecordBook2() {
        book.setGraduationWorkGrade(4);
        assertFalse(book.appliesForRedDiploma());

        book.setGraduationWorkGrade(5);
        assertTrue(book.appliesForRedDiploma());
    }

    public void testRecordBook3() {
        assertFalse(book.semesters[0].appliesForIncreasedStipend());
        assertTrue(book.semesters[1].appliesForIncreasedStipend());
    }
}