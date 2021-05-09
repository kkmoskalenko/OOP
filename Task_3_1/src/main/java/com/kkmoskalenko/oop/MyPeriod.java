package com.kkmoskalenko.oop;

public class MyPeriod {
    private final int days;
    private final int months;
    private final int years;

    public MyPeriod(int days, int months, int years) {
        this.days = days;
        this.months = months;
        this.years = years;
    }

    public int getDays() {
        return days;
    }

    public int getMonths() {
        return months;
    }

    public int getYears() {
        return years;
    }
}
