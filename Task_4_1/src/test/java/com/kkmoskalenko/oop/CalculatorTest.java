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
    }
}