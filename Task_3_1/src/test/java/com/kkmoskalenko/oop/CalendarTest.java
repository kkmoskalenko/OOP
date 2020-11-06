package com.kkmoskalenko.oop;

import junit.framework.TestCase;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class CalendarTest extends TestCase {
    private MyDate today;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        LocalDate now = LocalDate.now();
        today = new MyDate(
                now.getDayOfMonth(),
                now.getMonthValue(),
                now.getYear()
        );
    }

    public void test1() {
        // Какой день недели будет через 1024 дня?

        LocalDate date = LocalDate.now().plusDays(1024);
        int expected = date.getDayOfWeek().getValue();

        MyDate myDate = today.plusDays(1024);
        int actual = myDate.getDayOfWeek();

        assertEquals(expected, actual);
    }

    public void test2() {
        // Сколько лет, месяцев и дней назад был день победы 9 мая 1945 года?

        LocalDate date = LocalDate.of(1945, 5, 9);
        Period period = date.until(LocalDate.now());

        MyDate myDate = new MyDate(9, 5, 1945);
        MyPeriod myPeriod = myDate.until(today);

        assertEquals(period.getDays(), myPeriod.getDays());
        assertEquals(period.getMonths(), myPeriod.getMonths());
        assertEquals(period.getYears(), myPeriod.getYears());
    }

    public void test3() {
        // В какой день недели вы родились?

        LocalDate date = LocalDate.of(2001, 8, 8);
        int expected = date.getDayOfWeek().getValue();

        MyDate myDate = new MyDate(8, 8, 2001);
        int actual = myDate.getDayOfWeek();

        assertEquals(expected, actual);
    }

    public void test4() {
        // Какой месяц будет через 17 недель?

        LocalDate date = LocalDate.now().plusWeeks(17);
        int expected = date.getMonth().getValue();

        MyDate myDate = today.plusWeeks(17);
        int actual = myDate.getMonth();

        assertEquals(expected, actual);
    }

    public void test5() {
        // Сколько дней до нового года?

        LocalDate date = LocalDate.of(2021, 1, 1);
        long expected = LocalDate.now().until(date, ChronoUnit.DAYS);

        MyDate myDate = new MyDate(1, 1, 2021);
        long actual = today.daysUntil(myDate);

        assertEquals(expected, actual);
    }

    public void test6() {
        // Ближайшая пятница 13-го числа месяца?

        LocalDate date = LocalDate.now();
        MyDate myDate = today;

        if (date.getDayOfMonth() > 13) {
            date = date.plusMonths(1);
        }

        if (myDate.getDay() > 13) {
            myDate = myDate.plusMonths(1);
        }

        date = LocalDate.of(date.getYear(), date.getMonth(), 13);
        myDate = new MyDate(13, myDate.getMonth(), myDate.getYear());

        while (5 != date.getDayOfWeek().getValue()) {
            date = date.plusMonths(1);
        }

        while (5 != myDate.getDayOfWeek()) {
            myDate = myDate.plusMonths(1);
        }

        assertEquals(date.getDayOfMonth(), myDate.getDay());
        assertEquals(date.getMonthValue(), myDate.getMonth());
        assertEquals(date.getYear(), myDate.getYear());
    }
}