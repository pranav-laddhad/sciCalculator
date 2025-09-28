package com.scicalculator;

public class Calculator {

    // Square root
    public double sqrt(double x) {
        if (x < 0) {
            throw new IllegalArgumentException("Cannot compute square root of negative number.");
        }
        return Math.sqrt(x);
    }

    // Factorial
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative numbers.");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Natural logarithm
    public double ln(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("Logarithm only defined for positive numbers.");
        }
        return Math.log(x);
    }

    // Power
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}
