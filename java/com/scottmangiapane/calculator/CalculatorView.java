package com.scottmangiapane.calculator;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class CalculatorView {
    private Calculator calculator;
    private HorizontalScrollView hsv;
    private TextView displayPrimary;
    private TextView displaySecondary;
    private TextView[][] buttons;

    public CalculatorView(final AppCompatActivity activity) {
        hsv = (HorizontalScrollView) activity.findViewById(R.id.display_hsv);
        this.displayPrimary = (TextView) activity.findViewById(R.id.display_primary);
        this.displaySecondary = (TextView) activity.findViewById(R.id.display_secondary);
        this.buttons = new TextView[][]{
                {(TextView) activity.findViewById(R.id.text_view_7),
                        (TextView) activity.findViewById(R.id.text_view_8),
                        (TextView) activity.findViewById(R.id.text_view_9)},
                {(TextView) activity.findViewById(R.id.text_view_4),
                        (TextView) activity.findViewById(R.id.text_view_5),
                        (TextView) activity.findViewById(R.id.text_view_6)},
                {(TextView) activity.findViewById(R.id.text_view_1),
                        (TextView) activity.findViewById(R.id.text_view_2),
                        (TextView) activity.findViewById(R.id.text_view_3)},
                {(TextView) activity.findViewById(R.id.text_view_decimal),
                        (TextView) activity.findViewById(R.id.text_view_0),
                        (TextView) activity.findViewById(R.id.text_view_equals)},
                {(TextView) activity.findViewById(R.id.text_view_delete),
                        (TextView) activity.findViewById(R.id.text_view_divide),
                        (TextView) activity.findViewById(R.id.text_view_multiply),
                        (TextView) activity.findViewById(R.id.text_view_subtract),
                        (TextView) activity.findViewById(R.id.text_view_add)},
                {(TextView) activity.findViewById(R.id.text_view_sin),
                        (TextView) activity.findViewById(R.id.text_view_cos),
                        (TextView) activity.findViewById(R.id.text_view_tan)},
                {(TextView) activity.findViewById(R.id.text_view_ln),
                        (TextView) activity.findViewById(R.id.text_view_log),
                        (TextView) activity.findViewById(R.id.text_view_factorial)},
                {(TextView) activity.findViewById(R.id.text_view_pi),
                        (TextView) activity.findViewById(R.id.text_view_e),
                        (TextView) activity.findViewById(R.id.text_view_exponent)},
                {(TextView) activity.findViewById(R.id.text_view_start_parenthesis),
                        (TextView) activity.findViewById(R.id.text_view_end_parenthesis),
                        (TextView) activity.findViewById(R.id.text_view_square_root)}
        };
        for (int i1 = 0; i1 < buttons.length; i1++)
            for (int i2 = 0; i2 < buttons[i1].length; i2++) {
                final String input = (String) buttons[i1][i2].getText();
                buttons[i1][i2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (input.equals("."))
                            calculator.decimal();
                        if (input.equals("DEL"))
                            calculator.delete();
                        if (Character.isDigit(input.charAt(0)))
                            calculator.digit(input.charAt(0));
                        if (input.equals("="))
                            calculator.equal();
                        if (input.equals("π"))
                            calculator.num("π");
                        if (input.equals("e"))
                            calculator.num("e");
                        if (input.equals("÷"))
                            calculator.numOpNum('/');
                        if (input.equals("×"))
                            calculator.numOpNum('*');
                        if (input.equals("−"))
                            calculator.numOpNum('-');
                        if (input.equals("+"))
                            calculator.numOpNum('+');
                        if (input.equals("^"))
                            calculator.numOpNum('^');
                        if (input.equals("√"))
                            calculator.opNum('√');
                        if (input.equals("("))
                            calculator.parenthesisLeft();
                        if (input.equals(")"))
                            calculator.parenthesisRight();
                    }
                });
            }
        this.calculator = new Calculator(this);
    }

    public String getText() {
        return calculator.getText();
    }

    public void setText(String s) {
        calculator.setText(s);
    }

    private String formatToDisplayMode(String s) {
        s = s.replace(" ", "").replace("/", "÷").replace("*", "×").replace("-", "−");
        s = s.replace("×π", "π").replace("×e", "e").replace("×(", "(");
        s = s.replace("∞", "Infinity").replace("NaN", "Undefined");
        return s;
    }

    public void displayPrimary(String val) {
        displayPrimary.setText(formatToDisplayMode(val));
        ViewTreeObserver vto = hsv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                hsv.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }

    public void displaySecondary(String val) {
        displaySecondary.setText(formatToDisplayMode(val));
    }
}
