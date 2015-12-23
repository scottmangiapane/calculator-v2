package com.scottmangiapane.calculator;

public class Calculator {
    private CalculatorViewInterface view;
    private EquationSolver equationSolver;
    private String text;

    public Calculator(CalculatorViewInterface view) {
        this.view = view;
        this.equationSolver = new EquationSolver();
        this.text = "";
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

    public void operator(char operator) {
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

    public void pi() {
        if (text.length() >= 3
                && isOperator(text.charAt(text.length() - 1))
                && isNumber(text.charAt(text.length() - 3)))
            text += " ";
        if (text.length() > 0
                && isNumber(text.charAt(text.length() - 1)))
            text += " * ";
        if (text.length() == 0 || (text.length() > 0 && text.charAt(text.length() - 1) != '.'))
            text += "π";
        update();
    }

    public void e() {
        if (text.length() >= 3
                && isOperator(text.charAt(text.length() - 1))
                && isNumber(text.charAt(text.length() - 3)))
            text += " ";
        if (text.length() > 0
                && isNumber(text.charAt(text.length() - 1)))
            text += " * ";
        if (text.length() == 0 || (text.length() > 0 && text.charAt(text.length() - 1) != '.'))
            text += "e";
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

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
        update();
    }

    private boolean containsOperator(String s) {
        if (s.contains("/")
                || s.contains("*")
                || s.contains("-")
                || s.contains("+")
                || s.contains("^"))
            return true;
        return false;
    }

    private boolean containsPaddedOperator(String s) {
        if (s.contains(" / ")
                || s.contains(" * ")
                || s.contains(" - ")
                || s.contains(" + ")
                || s.contains(" ^ "))
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
        if (Character.isDigit(c) || c == 'π' || c == 'e')
            return true;
        return false;
    }

    private boolean isOperator(char c) {
        if (c == '/' || c == '*' || c == '-' || c == '+' || c == '^')
            return true;
        return false;
    }

    private void update() {
        text = text.replace(" * e * ", "E");
        text = text.replace(" * e - ", "E-");
        view.displayPrimary(text);
        view.displaySecondary("");
        if (containsPaddedOperator(text))
            try {
                view.displaySecondary(equationSolver.formatNumber(equationSolver.evaluateExpression(text)));
            } catch (Exception e) {
            }
    }
}
