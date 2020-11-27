package com.kkmoskalenko.oop;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    public static double evaluate(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split(" ");

        for (int i = tokens.length - 1; i >= 0; i--) {
            try {
                stack.push(Double.parseDouble(tokens[i]));
            } catch (NumberFormatException e) {
                switch (tokens[i]) {
                    case "+" -> stack.push(stack.pop() + stack.pop());
                    case "-" -> stack.push(stack.pop() - stack.pop());
                    case "*" -> stack.push(stack.pop() * stack.pop());
                    case "/" -> stack.push(stack.pop() / stack.pop());

                    case "log" -> stack.push(Math.log(stack.pop()));
                    case "pow" -> stack.push(Math.pow(stack.pop(), stack.pop()));
                    case "sqrt" -> stack.push(Math.sqrt(stack.pop()));
                    case "sin" -> stack.push(Math.sin(stack.pop()));
                    case "cos" -> stack.push(Math.cos(stack.pop()));

                    default -> throw new RuntimeException("Invalid operator: " + tokens[i]);
                }
            }
        }

        return stack.peek();
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
