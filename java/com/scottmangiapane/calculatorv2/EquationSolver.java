package com.scottmangiapane.calculatorv2;

import org.apache.commons.math3.special.Gamma;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class EquationSolver {
    public String evaluateExpression(String s) {
        s = s.replace("π", "" + Math.PI).replace("e", "" + Math.E)
                .replace("n ", "ln ( ").replace("l ", "log ( ").replace("√ ", "√ ( ")
                .replace("s ", "sin ( ").replace("c ", "cos ( ").replace("t ", "tan ( ");
        s = step1(s);
        return s;
    }

    private String step1(String s) {
        while (numOfOccurrences('(', s) > numOfOccurrences(')', s))
            s += ") ";
        while (s.contains("(")) {
            int startIndex = s.indexOf('(');
            int endIndex = 0;
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
                    + step1(s.substring(startIndex + 2, endIndex))
                    + " " + s.substring(endIndex + 2);
        }
        while (s.contains("ln")) {
            int startIndex = s.indexOf("ln");
            int endIndex = s.indexOf(' ', startIndex + 3);
            s = s.substring(0, startIndex)
                    + Math.log(Double.parseDouble(step1(s.substring(startIndex + 3, endIndex))))
                    + s.substring(endIndex);
        }
        while (s.contains("log")) {
            int startIndex = s.indexOf("log");
            int endIndex = s.indexOf(' ', startIndex + 4);
            s = s.substring(0, startIndex)
                    + Math.log10(Double.parseDouble(step1(s.substring(startIndex + 4, endIndex))))
                    + s.substring(endIndex);
        }
        while (s.contains("sin")) {
            int startIndex = s.indexOf("sin");
            int endIndex = s.indexOf(' ', startIndex + 4);
            s = s.substring(0, startIndex)
                    + Math.sin(Double.parseDouble(step1(s.substring(startIndex + 4, endIndex))))
                    + s.substring(endIndex);
        }
        while (s.contains("cos")) {
            int startIndex = s.indexOf("cos");
            int endIndex = s.indexOf(' ', startIndex + 4);
            s = s.substring(0, startIndex)
                    + Math.cos(Double.parseDouble(step1(s.substring(startIndex + 4, endIndex))))
                    + s.substring(endIndex);
        }
        while (s.contains("tan")) {
            int startIndex = s.indexOf("tan");
            int endIndex = s.indexOf(' ', startIndex + 4);
            s = s.substring(0, startIndex)
                    + Math.tan(Double.parseDouble(step1(s.substring(startIndex + 4, endIndex))))
                    + s.substring(endIndex);
        }
        while (s.contains("√")) {
            int startIndex = s.indexOf('√');
            int endIndex = s.indexOf(' ', startIndex + 2);
            s = s.substring(0, startIndex)
                    + Math.sqrt(Double.parseDouble(step1(s.substring(startIndex + 2, endIndex))))
                    + s.substring(endIndex);
        }
        while (s.contains("!")) {
            s = " " + s + " ";
            String s1 = "";
            String s2 = s.substring(s.lastIndexOf(" ", s.indexOf("!") - 2) + 1, s.indexOf("!") - 1);
            String s3 = "";
            try {
                s1 = s.substring(1, s.lastIndexOf(" ", s.indexOf("!") - 2));
            } catch (Exception e) {
            }
            s2 = "" + Gamma.gamma(Double.parseDouble(s2) + 1);
            try {
                s3 = s.substring(s.indexOf("!") + 2);
            } catch (Exception e) {
            }
            s = s1 + " " + s2 + " " + s3;
        }
        s = step2(s, " ^ ", " ^ ");
        s = step2(s, " * ", " / ");
        s = step2(s, " + ", " - ");
        return s;
    }

    private String step2(String s, String op1, String op2) {
        s = " " + s + " ";
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

    public String formatNumber(String s) {
        if (s.contains("∞") || s.contains("Infinity") || s.contains("NaN"))
            return s;
        DecimalFormat df = new DecimalFormat("#.########E0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String output = df.format(Double.parseDouble(s));
        if (Math.abs(Double.parseDouble(output.substring(output.indexOf("E") + 1))) < 8) {
            df.applyPattern("#.########");
            output = df.format(Double.parseDouble(s));
        }
        return output;
    }

    private int numOfOccurrences(char c, String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == c)
                count++;
        return count;
    }
}
