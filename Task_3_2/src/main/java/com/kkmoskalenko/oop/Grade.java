package com.kkmoskalenko.oop;

public enum Grade {
    UNDEFINED, POOR, WEAK, SATISFACTORY, GOOD, EXCELLENT;

    public static Grade fromRawValue(int grade) {
        if (grade < 1 || grade > 5) {
            throw new RuntimeException("Invalid grade");
        }

        return Grade.values()[grade];
    }
}
