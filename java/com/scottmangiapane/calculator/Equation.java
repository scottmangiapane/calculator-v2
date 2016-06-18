package com.scottmangiapane.calculator;

import java.util.ArrayList;

public class Equation extends ArrayList<String> {

    public String getText() {
        String text = "";
        for (String s : this)
            text += s + " ";
        return text;
    }

    public void setText(String text) {
        while (size() > 0)
            removeLast();
        if (text.length() > 0) {
            String[] sa = text.split(" ");
            for (String s : sa)
                add(s);
        }
    }

    public void attachToLast(char c) {
        if (size() == 0)
            add("" + c);
        else
            set(size() - 1, getLast() + c);
    }

    public void detachFromLast() {
        if (getLast().length() > 0) {
            set(size() - 1, getLast().substring(0, getLast().length() - 1));
        }
    }

    public void removeLast() {
        if (size() > 0)
            remove(size() - 1);
    }

    public String getLast() {
        return getRecent(0);
    }

    public char getLastChar() {
        String s = getLast();
        if (s.length() > 0)
            return s.charAt(s.length() - 1);
        return ' ';
    }

    public String getRecent(int indexFromLast) {
        if (size() <= indexFromLast)
            return "";
        return get(size() - indexFromLast - 1);
    }

    public boolean isNumber(int i) {
        String s = getRecent(i);
        if (s != null && s.length() > 0) {
            char c = s.charAt(0);
            if (isRawNumber(i) || c == 'π' || c == 'e' || c == ')' || c == '!')
                return true;
        }
        return false;
    }

    public boolean isOperator(int i) {
        String s = getRecent(i);
        if (s != null && s.length() == 1) {
            char c = s.charAt(0);
            if (c == '/' || c == '*' || c == '-' || c == '+' || c == '^')
                return true;
        }
        return false;
    }

    public boolean isRawNumber(int i) {
        String s = getRecent(i);
        if (s != null && s.length() > 0)
            if (Character.isDigit(s.charAt(0)) || (s.charAt(0) == '-' && isStartCharacter(i + 1) && (s.length() == 1 || Character.isDigit(s.charAt(1)))))
                return true;
        return false;
    }

    public boolean isStartCharacter(int i) {
        String s = getRecent(i);
        if (s != null && s.length() > 0) {
            char c = s.charAt(0);
            if (s.length() > 1 && c == '-')
                c = s.charAt(1);
            if (c == '√' || c == 's' || c == 'c' || c == 't' || c == 'n' || c == 'l' || c == '(' || c == '/' || c == '*' || c == '-' || c == '+' || c == '^') // todo optimize
                return true;
        }
        if (s.equals(""))
            return true;
        return false;
    }

    public int numOf(char c) {
        int count = 0;
        for (int i = 0; i < getText().length(); i++)
            if (getText().charAt(i) == c)
                count++;
        return count;
    }
}
