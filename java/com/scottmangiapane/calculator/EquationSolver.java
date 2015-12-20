package com.scottmangiapane.calculator;

import java.text.DecimalFormat;

public class EquationSolver {
    public String evaluateExpression(String s) {
        while (s.contains("(")) {
            int startIndex = s.indexOf('(');
            int endIndex = startIndex;
            for (int i = startIndex, layer = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(')
                    layer++;
                if (s.charAt(i) == ')')
                    layer--;
                if (layer == 0) {
                    endIndex = i;
                    break;
                }
            }
            s = s.substring(0, startIndex)
                    + evaluateExpression(s.substring(startIndex + 1, endIndex))
                    + s.substring(endIndex + 1);
        }
        s = operation(s, " ^ ", " ^ ");
        s = operation(s, " * ", " / ");
        s = operation(s, " + ", " - ");
        return s;
    }

    public String formatNumber(String s) {
        s = (new DecimalFormat("#.######")).format(Double.parseDouble(s));
        if (s.indexOf('.') > 10 || (!s.contains(".") && s.length() > 10))
            s = (new DecimalFormat("0.######E0")).format(Double.parseDouble(s));
        return s;
    }

    private String operation(String s, String op1, String op2) {
        s = " " + s.replace("π", "" + Math.PI) + " ";
        while (s.contains(op1) || s.contains(op2)) {
            String operator;
            if (s.indexOf(op2) < s.indexOf(op1))
                operator = op2;
            else
                operator = op1;
            if (!s.contains(op1))
                operator = op2;
            if (!s.contains(op2))
                operator = op1;
            String s1 = s.substring(0, s.indexOf(operator));
            String s2 = s.substring(s.indexOf(operator) + 3);
            double d1 = Double.parseDouble(s1.substring(s1.lastIndexOf(' ') + 1));
            double d2 = Double.parseDouble(s2.substring(0, s2.indexOf(' ')));
            s1 = s1.substring(0, s1.lastIndexOf(' ')).trim();
            s2 = s2.substring(s2.indexOf(' '), s2.length()).trim();
            double answer = 0;
            switch (operator) {
                case " / ":
                    answer = d1 / d2;
                    break;
                case " * ":
                    answer = d1 * d2;
                    break;
                case " - ":
                    answer = d1 - d2;
                    break;
                case " + ":
                    answer = d1 + d2;
                    break;
                case " ^ ":
                    answer = Math.pow(d1, d2);
                    break;
            }
            s = " " + s1 + " " + answer + " " + s2 + " ";
        }
        return s.trim();
    }
}
