package com.kkmoskalenko.oop;

public class MyDate {
    // The number of days in a 400 year cycle.
    private static final int DAYS_PER_CYCLE = 146097;

    // The number of days from year zero to year 1970.
    private static final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

    private final int day;
    private final int month;
    private final int year;

    // Getters
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Initializers
    public MyDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            throw new RuntimeException("Invalid month");
        }

        if (day < 1 || day > 31 || day > lengthOfMonth(month, year)) {
            throw new RuntimeException("Invalid day");
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    // Epoch Day is based on the epoch 01.01.1970
    public MyDate(long epochDay) {
        long zeroDay = epochDay + DAYS_0000_TO_1970 - 60;
        long adjust = 0;

        if (zeroDay < 0) {
            long adjustCycles = (zeroDay + 1) / DAYS_PER_CYCLE - 1;
            adjust = adjustCycles * 400;
            zeroDay += -adjustCycles * DAYS_PER_CYCLE;
        }

        long yearEst = (400 * zeroDay + 591) / DAYS_PER_CYCLE;
        long doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);

        if (doyEst < 0) {
            yearEst--;
            doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        }

        yearEst += adjust;

        int marchDoy0 = (int) doyEst;
        int marchMonth0 = (marchDoy0 * 5 + 2) / 153;
        int month = (marchMonth0 + 2) % 12 + 1;
        int dom = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1;

        yearEst += marchMonth0 / 10;

        this.day = dom;
        this.month = month;
        this.year = (int) yearEst;
    }

    // Instance methods
    public long toEpochDay() {
        long y = year;
        long m = month;
        long total = 365 * y;

        if (y >= 0) {
            total += (y + 3) / 4 - (y + 99) / 100 + (y + 399) / 400;
        } else {
            total -= y / -4 - y / -100 + y / -400;
        }

        total += ((367 * m - 362) / 12);
        total += day - 1;

        if (m > 2) {
            total--;

            if (!isLeapYear(year)) {
                total--;
            }
        }

        return total - DAYS_0000_TO_1970;
    }

    public MyDate plusDays(long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }

        long epochDay = toEpochDay() + daysToAdd;
        return new MyDate(epochDay);
    }

    public MyDate plusWeeks(long weeksToAdd) {
        return plusDays(weeksToAdd * 7);
    }

    public MyDate plusMonths(long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }

        long monthCount = year * 12L + (month - 1);
        long calcMonths = monthCount + monthsToAdd;
        int newYear = (int) Math.floorDiv(calcMonths, 12);
        int newMonth = Math.floorMod(calcMonths, 12) + 1;
        int newDay = Math.min(day, MyDate.lengthOfMonth(newMonth, newYear));

        return new MyDate(newDay, newMonth, newYear);
    }

    public int getDayOfWeek() {
        // 1 for Monday and so on
        return Math.floorMod(toEpochDay() + 3, 7) + 1;
    }

    public long daysUntil(MyDate end) {
        return end.toEpochDay() - toEpochDay();
    }

    public MyPeriod until(MyDate end) {
        long totalMonths = (end.year - this.year) * 12
                + end.month - this.month;
        int days = end.day - this.day;

        if (totalMonths > 0 && days < 0) {
            totalMonths--;
            MyDate calcDate = this.plusMonths(totalMonths);
            days = (int) (end.toEpochDay() - calcDate.toEpochDay());
        } else if (totalMonths < 0 && days > 0) {
            totalMonths++;
            days -= MyDate.lengthOfMonth(end.month, end.year);
        }

        int years = (int) totalMonths / 12;
        int months = (int) (totalMonths % 12);

        return new MyPeriod(days, months, years);
    }

    // Static methods
    private static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }

        if (year % 100 == 0) {
            return false;
        }

        return year % 4 == 0;
    }

    private static int lengthOfMonth(int month, int year) {
        return switch (month) {
            case 2 -> (isLeapYear(year) ? 29 : 28);
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }
}

