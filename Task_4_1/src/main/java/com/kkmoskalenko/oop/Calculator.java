package com.kkmoskalenko.oop;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Calculator {
    public static double evaluate(String expression) {
        Deque<Double> deque = new ArrayDeque<>();
        String[] tokens = expression.split(" ");

        for (int i = tokens.length - 1; i >= 0; i--) {
            try {
                deque.push(Double.parseDouble(tokens[i]));
            } catch (NumberFormatException e) {
                switch (tokens[i]) {
                    case "+" -> deque.push(deque.pop() + deque.pop());
                    case "-" -> deque.push(deque.pop() - deque.pop());
                    case "*" -> deque.push(deque.pop() * deque.pop());
                    case "/" -> deque.push(deque.pop() / deque.pop());

                    case "log" -> deque.push(Math.log(deque.pop()));
                    case "pow" -> deque.push(Math.pow(deque.pop(), deque.pop()));
                    case "sqrt" -> deque.push(Math.sqrt(deque.pop()));
                    case "sin" -> deque.push(Math.sin(deque.pop()));
                    case "cos" -> deque.push(Math.cos(deque.pop()));

                    default -> throw new RuntimeException("Invalid operator: " + tokens[i]);
                }
            }
        }

        return deque.peek();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                String expression = scanner.nextLine();
                System.out.println(evaluate(expression));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
