package com.scottmangiapane.calculator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class CalculatorView {
    private Calculator calculator;
    private TextView displayPrimary;
    private TextView displaySecondary;
    private HorizontalScrollView hsv;

    public CalculatorView(final AppCompatActivity activity) {
        displayPrimary = (TextView) activity.findViewById(R.id.display_primary);
        displaySecondary = (TextView) activity.findViewById(R.id.display_secondary);
        hsv = (HorizontalScrollView) activity.findViewById(R.id.display_hsv);
        TextView[] digits = {
                (TextView) activity.findViewById(R.id.button_0),
                (TextView) activity.findViewById(R.id.button_1),
                (TextView) activity.findViewById(R.id.button_2),
                (TextView) activity.findViewById(R.id.button_3),
                (TextView) activity.findViewById(R.id.button_4),
                (TextView) activity.findViewById(R.id.button_5),
                (TextView) activity.findViewById(R.id.button_6),
                (TextView) activity.findViewById(R.id.button_7),
                (TextView) activity.findViewById(R.id.button_8),
                (TextView) activity.findViewById(R.id.button_9)};
        for (int i = 0; i < digits.length; i++) {
            final String id = (String) digits[i].getText();
            digits[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculator.digit(id.charAt(0));
                }
            });
        }
        TextView[] buttons = {
                (TextView) activity.findViewById(R.id.button_sin),
                (TextView) activity.findViewById(R.id.button_cos),
                (TextView) activity.findViewById(R.id.button_tan),
                (TextView) activity.findViewById(R.id.button_ln),
                (TextView) activity.findViewById(R.id.button_log),
                (TextView) activity.findViewById(R.id.button_factorial),
                (TextView) activity.findViewById(R.id.button_pi),
                (TextView) activity.findViewById(R.id.button_e),
                (TextView) activity.findViewById(R.id.button_exponent),
                (TextView) activity.findViewById(R.id.button_start_parenthesis),
                (TextView) activity.findViewById(R.id.button_end_parenthesis),
                (TextView) activity.findViewById(R.id.button_square_root),
                (TextView) activity.findViewById(R.id.button_add),
                (TextView) activity.findViewById(R.id.button_subtract),
                (TextView) activity.findViewById(R.id.button_multiply),
                (TextView) activity.findViewById(R.id.button_divide),
                (TextView) activity.findViewById(R.id.button_decimal),
                (TextView) activity.findViewById(R.id.button_equals)};
        for (int i = 0; i < buttons.length; i++) {
            final String id = (String) buttons[i].getText();
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id.equals("sin"))
                        calculator.opNum('s');
                    if (id.equals("cos"))
                        calculator.opNum('c');
                    if (id.equals("tan"))
                        calculator.opNum('t');
                    if (id.equals("ln"))
                        calculator.opNum('n');
                    if (id.equals("log"))
                        calculator.opNum('l');
                    if (id.equals("!"))
                        calculator.numOp('!');
                    if (id.equals("π"))
                        calculator.num('π');
                    if (id.equals("e"))
                        calculator.num('e');
                    if (id.equals("^"))
                        calculator.numOpNum('^');
                    if (id.equals("("))
                        calculator.parenthesisLeft();
                    if (id.equals(")"))
                        calculator.parenthesisRight();
                    if (id.equals("√"))
                        calculator.opNum('√');
                    if (id.equals("÷"))
                        calculator.numOpNum('/');
                    if (id.equals("×"))
                        calculator.numOpNum('*');
                    if (id.equals("−"))
                        calculator.numOpNum('-');
                    if (id.equals("+"))
                        calculator.numOpNum('+');
                    if (id.equals("."))
                        calculator.decimal();
                    if (id.equals("=") && !getText().equals(""))
                        calculator.equal();
                }
            });
        }
        activity.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.delete();
            }
        });
        activity.findViewById(R.id.button_delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!displayPrimary.getText().toString().trim().equals("")) {
                    final View displayOverlay = activity.findViewById(R.id.display_overlay);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Animator circle = ViewAnimationUtils.createCircularReveal(
                                displayOverlay,
                                displayOverlay.getMeasuredWidth(),
                                displayOverlay.getMeasuredHeight() / 2,
                                0,
                                (int) Math.hypot(displayOverlay.getWidth(), displayOverlay.getHeight()));
                        circle.setDuration(300);
                        circle.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                calculator.setText("");
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                calculator.setText("");
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        });
                        ObjectAnimator fade = ObjectAnimator.ofFloat(displayOverlay, "alpha", 0f);
                        fade.setInterpolator(new DecelerateInterpolator());
                        fade.setDuration(200);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playSequentially(circle, fade);
                        displayOverlay.setAlpha(1);
                        animatorSet.start();
                    } else
                        calculator.setText("");
                }
                return false;
            }
        });
        calculator = new Calculator(this);
    }

    public String getText() {
        return calculator.getText();
    }

    public void setText(String s) {
        calculator.setText(s);
    }

    public void displayPrimaryScrollLeft(String val) {
        displayPrimary.setText(formatToDisplayMode(val));
        ViewTreeObserver vto = hsv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                hsv.fullScroll(View.FOCUS_LEFT);
            }
        });
    }

    public void displayPrimaryScrollRight(String val) {
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

    private String formatToDisplayMode(String s) {
        return s.replace("/", "÷").replace("*", "×").replace("-", "−")
                .replace("n ", "ln(").replace("l ", "log(").replace("√ ", "√(")
                .replace("s ", "sin(").replace("c ", "cos(").replace("t ", "tan(")
                .replace(" ", "").replace("∞", "Infinity").replace("NaN", "Undefined");
    }
}
