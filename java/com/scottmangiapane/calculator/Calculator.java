package com.scottmangiapane.calculator;

public class Calculator {
    private CalculatorView view;
    private EquationSolver equationSolver;
    private String text;

    public Calculator(CalculatorView view) {
        this.view = view;
        this.equationSolver = new EquationSolver();
        this.text = "";
        update();
    }

    public void decimal() {
        if (getLast().indexOf('.') == -1)
            if (text.length() > 0 && Character.isDigit(text.charAt(text.length() - 1)))
                text += ".";
            else {
                digit('0');
                decimal();
            }
        update();
    }

    public void delete() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            if (text.length() > 0 && text.charAt(text.length() - 1) == ' ')
                text = text.substring(0, text.length() - 1);
        }
        update();
    }

    public void digit(char digit) {
        if (text.length() >= 3
                && isOperator(text.charAt(text.length() - 1))
                && isNumber(text.charAt(text.length() - 3)))
            text += " ";
        if (text.length() > 0
                && isNumber(text.charAt(text.length() - 1))
                && !Character.isDigit(text.charAt(text.length() - 1)))
            text += " * ";
        text += "" + digit;
        update();
    }

    public void equal() {
        try {
            text = equationSolver.formatNumber(equationSolver.evaluateExpression(text));
            view.displayPrimary(text);
        } catch (Exception e) {
            text = "";
            view.displayPrimary("Error");
        }
        view.displaySecondary("");
        text = "";
    }

    public void num(String number) {
        if (text.length() >= 3
                && isOperator(text.charAt(text.length() - 1))
                && isNumber(text.charAt(text.length() - 3)))
            text += " ";
        if (text.length() > 0
                && isNumber(text.charAt(text.length() - 1)))
            text += " * ";
        if (text.length() == 0 || (text.length() > 0 && text.charAt(text.length() - 1) != '.'))
            text += number;
        update();
    }

    public void numOpNum(char operator) {
        if (operator == '-' && (text.length() == 0
                || (isOperator(text.charAt(text.length() - 1))
                && (text.length() >= 3
                && isNumber(text.charAt(text.length() - 3)))))) {
            text = (text + " " + operator).trim();
        } else if (text.length() > 0 && isOperator(text.charAt(text.length() - 1))) {
            if (text.length() >= 3 && isOperator(text.charAt(text.length() - 3)))
                text = text.substring(0, text.length() - 3) + operator;
            else if (text.length() >= 3)
                text = text.substring(0, text.length() - 1) + operator;
        } else if (text.length() > 0 && isNumber(text.charAt(text.length() - 1)))
            text += " " + operator;
        update();
    }

    public void opNum(char c) {
        if (text.length() >= 3
                && isOperator(text.charAt(text.length() - 1))
                && isNumber(text.charAt(text.length() - 3)))
            text += " ";
        if (text.length() > 0
                && isNumber(text.charAt(text.length() - 1)))
            text += " * ";
        if (text.length() == 0 || (text.length() > 0 && text.charAt(text.length() - 1) != '.'))
            text += "" + c;
        update();
    }

    public void parenthesisLeft() {
        if (text.length() >= 3
                && isOperator(text.charAt(text.length() - 1))
                && isNumber(text.charAt(text.length() - 3)))
            text += " ";
        if (text.length() > 0
                && isNumber(text.charAt(text.length() - 1)))
            text += " * ";
        if (text.length() == 0 || (text.length() > 0 && text.charAt(text.length() - 1) != '.'))
            text += "(";
        update();
    }

    public void parenthesisRight() {
        if (numOfOccurrences('(', text) > numOfOccurrences(')', text)
                && isNumber(text.charAt(text.length() - 1)))
            text += ")";
        update();
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
        update();
    }

    private boolean containsOperator(String s) {
        if (s.contains(" / ")
                || s.contains(" * ")
                || s.contains(" - ")
                || s.contains(" + ")
                || s.contains(" ^ ")
                || s.contains("√"))
            return true;
        return false;
    }

    private String getLast() {
        String[] array = text.split(" ");
        if (array != null && array.length > 0)
            return array[array.length - 1];
        return "";
    }

    private boolean isNumber(char c) {
        if (Character.isDigit(c) || c == 'π' || c == 'e' || c == ')')
            return true;
        return false;
    }

    private boolean isOperator(char c) {
        if (c == '/' || c == '*' || c == '-' || c == '+' || c == '^')
            return true;
        return false;
    }

    private int numOfOccurrences(char c, String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == c)
                count++;
        return count;
    }

    private void update() {
        view.displayPrimaryAndScroll(text);
        view.displaySecondary("");
        if (containsOperator(text))
            try {
                view.displaySecondary(equationSolver.formatNumber(equationSolver
                        .evaluateExpression(text)));
            } catch (Exception e) {
            }
    }
}
