package com.scicalculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Scientific Calculator =====");
            System.out.println("1. Square Root (√x)");
            System.out.println("2. Factorial (!x)");
            System.out.println("3. Natural Logarithm (ln(x))");
            System.out.println("4. Power Function (x^b)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter a number: ");
                        double num1 = scanner.nextDouble();
                        try {
                            System.out.println("√" + num1 + " = " + calc.sqrt(num1));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Enter a non-negative integer: ");
                        int n = scanner.nextInt();
                        try {
                            System.out.println(n + "! = " + calc.factorial(n));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("Enter a positive number: ");
                        double num2 = scanner.nextDouble();
                        try {
                            System.out.println("ln(" + num2 + ") = " + calc.ln(num2));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Enter base (x): ");
                        double base = scanner.nextDouble();
                        System.out.print("Enter exponent (b): ");
                        double exp = scanner.nextDouble();
                        System.out.println(base + "^" + exp + " = " + calc.power(base, exp));
                        break;

                    case 5:
                        System.out.println("Exiting calculator. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Please choose again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
}
