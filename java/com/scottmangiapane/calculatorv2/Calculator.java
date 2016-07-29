package com.scottmangiapane.calculatorv2;

import android.content.Context;

public class Calculator {
    private MainView view;
    private EquationSolver equationSolver;
    private Equation eq;

    public Calculator(Context context, MainView view) {
        this.view = view;
        this.equationSolver = new EquationSolver(context);
        this.eq = new Equation();
        update();
    }

    public String getText() {
        return eq.getText();
    }

    public void setText(String text) {
        eq.setText(text);
        update();
    }

    public void decimal() {
        if (!Character.isDigit(eq.getLastChar()))
            digit('0');
        if (!eq.getLast().contains("."))
            eq.attachToLast('.');
        update();
    }

    public void delete() {

        if (eq.getLast().length() > 1 && (eq.isRawNumber(0) || eq.getLast().charAt(0) == '-'))
            eq.detachFromLast();
        else
            eq.removeLast();
        update();
    }

    public void digit(char digit) {
        if (eq.isRawNumber(0))
            eq.attachToLast(digit);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + digit);
        }
        update();
    }

    public void equal() {
        String s;
        try {
            s = equationSolver.formatNumber(equationSolver.evaluateExpression(getText()));
        } catch (Exception e) {
            s = "Error";
        }
        view.displayPrimaryScrollLeft(s);
        view.displaySecondary("");
        eq = new Equation();
        if (!s.contains("∞") && !s.contains("Infinity") && !s.contains("NaN"))
            eq.add(s);
    }

    public void num(char number) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.isRawNumber(0) && eq.getLastChar() == '-')
            eq.attachToLast(number);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + number);
        }
        update();
    }

    public void numOp(char operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.isNumber(0))
            eq.add("" + operator);
        update();
    }

    public void numOpNum(char operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (operator != '-' || (eq.isOperator(0) && eq.isStartCharacter(1)))
            while (eq.isOperator(0))
                eq.removeLast();
        if (operator == '-' || !eq.isStartCharacter(0))
            eq.add("" + operator);
        update();
    }

    public void opNum(char operator) {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.getLast().equals("-"))
            eq.attachToLast(operator);
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("" + operator);
        }
        update();
    }

    public void parenthesisLeft() {
        if (eq.getLast().endsWith("."))
            eq.detachFromLast();
        if (eq.getLast().equals("-") && eq.isRawNumber(0))
            eq.attachToLast('(');
        else {
            if (eq.isNumber(0))
                eq.add("*");
            eq.add("(");
        }
        update();
    }

    public void parenthesisRight() {
        if (eq.numOf('s') + eq.numOf('c') + eq.numOf('t') + eq.numOf('n') + eq.numOf('l')
                + eq.numOf('√') + eq.numOf('(') > eq.numOf(')') && eq.isNumber(0))
            eq.add(")");
        update();
    }

    private void update() {
        view.displayPrimaryScrollRight(getText());
        try {
            view.displaySecondary(
                    equationSolver.formatNumber(equationSolver.evaluateExpression(getText()))
            );
        } catch (Exception e) {
            view.displaySecondary("");
        }
    }
}
