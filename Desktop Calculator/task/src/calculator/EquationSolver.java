package calculator;

import java.util.Stack;
import java.util.StringTokenizer;

public class EquationSolver{
    String equation;

    public static double solve(String equation) throws Exception {
        EquationSolver solver = new EquationSolver(equation);
        return solver.solveEquation();
    }

    public static int solveInt(String equation) throws Exception {
        return (int) solve(equation);
    }

    public EquationSolver(String equation){
        this.equation = equation;
    }

    public double solveEquation() throws Exception {
        String equation = this.equation;
        equation = equation.replaceAll("\\+\\)", ")+");
        equation = equation.replaceAll("\\(\\)", "");
        return evaluatePostFix(infixToPostfix(equation));
    }

    private double evaluatePostFix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(postfix);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

            if (Character.isDigit(token.charAt(0))) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                double b = stack.pop();
                if (token.charAt(0) == '√') {
                    stack.push(Math.sqrt(b));
                }else {
                    try {
                        double a = stack.pop();
                        stack.push(applyOperation(token.charAt(0), a, b));
                    }catch (Exception e){
                        if(token.charAt(0) == '-'){
                            stack.push(-1 * b);
                        }else {
/*                        if(token.charAt(0) == '+'){
                            stack.push(b);
                        }*/
                            throw new Exception("xxx");
                        }
                    }
                }
            }
        }

        double result = stack.pop();
        if(Double.isNaN(result)){
            throw new Exception("Invalid equation");
        }
        return result;
    }

    private Double applyOperation(char operator, double a, double b) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '×':
                return a * b;
            case '÷':
                if (b == 0)
                    throw new Exception("Division by zero");
                return a / b;
            case '√':
                return Math.sqrt(b);
            case '^':
                return Math.pow(a, b);
            default:
                throw new Exception("Unknown operator");
        }
    }

    private boolean isOperator(char c) {
//        return c == '+' || c == '-' || c == 'x' || c == '/';
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '√' || c == '^';
    }

    private String infixToPostfix(String equation) throws Exception {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(equation, "()+-×÷√^ ", true);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();

            if (token.isEmpty()) continue;

            char c = token.charAt(0);

            // If the token is a number, add it to the output
            if (Character.isDigit(c)) {
                result.append(token).append(' ');
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                if (!stack.isEmpty() && stack.peek() != '(') {
                    throw new Exception("Invalid equation"); // invalid expression
                } else {
                    stack.pop();
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }

        return result.toString();
    }

    private int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '×':
            case '÷':
                return 2;
            case '√':
            case '^':
                return 3;
            default:
                return -1;
        }
    }
}
