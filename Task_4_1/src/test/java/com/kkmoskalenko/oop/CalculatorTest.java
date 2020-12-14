package com.kkmoskalenko.oop;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {
    public void testEvaluate() {
        assertEquals(21.0, Calculator.evaluate("+ 9 * 2 6"));
        assertEquals(8.0, Calculator.evaluate("- + 8 / 6 3 2"));
        assertEquals(25.0, Calculator.evaluate("- + 7 * 4 5 + 2 0"));

        assertEquals(0.0, Calculator.evaluate("sin + - 1 2 1"));
        assertEquals(25.0, Calculator.evaluate("pow + 3 2 2"));
        assertEquals(5.0, Calculator.evaluate("sqrt * 5 + 1 4"));

        assertEquals(41607.731092436974, Calculator
                .evaluate("+ 98 * - * 22 31 * / 5 + 82 37 36 61"));
        assertEquals(-19529.652173913048, Calculator
                .evaluate("- * 29 - * 44 / + 75 93 23 * 62 16 82"));
    }
}