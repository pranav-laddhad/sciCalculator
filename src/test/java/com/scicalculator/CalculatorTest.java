package com.scicalculator;

import org.junit.jupiter.api.Test;

import com.scicalculator.Calculator;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calc = new Calculator();

    @Test
    void testSqrtPositive() {
        assertEquals(5.0, calc.sqrt(25), 0.0001);
    }

    @Test
    void testSqrtNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.sqrt(-9));
    }

    @Test
    void testFactorial() {
        assertEquals(120, calc.factorial(5));
    }

    @Test
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3));
    }

    @Test
    void testLnPositive() {
        assertEquals(0.0, calc.ln(1), 0.0001);
    }

    @Test
    void testLnNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.ln(-1));
    }

    @Test
    void testPower() {
        assertEquals(8.0, calc.power(2, 3), 0.0001);
    }
}
