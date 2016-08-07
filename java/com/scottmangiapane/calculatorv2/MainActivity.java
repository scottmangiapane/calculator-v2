package com.scottmangiapane.calculatorv2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    private TextView displayPrimary;
    private TextView displaySecondary;
    private HorizontalScrollView hsv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sp.getBoolean("pref_dark", false))
            switch (sp.getString("pref_theme", "0")) {
                case "0":
                    setTheme(R.style.AppTheme_Dark_Blue);
                    break;
                case "1":
                    setTheme(R.style.AppTheme_Dark_Cyan);
                    break;
                case "2":
                    setTheme(R.style.AppTheme_Dark_Gray);
                    break;
                case "3":
                    setTheme(R.style.AppTheme_Dark_Green);
                    break;
                case "4":
                    setTheme(R.style.AppTheme_Dark_Purple);
                    break;
                case "5":
                    setTheme(R.style.AppTheme_Dark_Red);
                    break;
            }
        else
            switch (sp.getString("pref_theme", "0")) {
                case "0":
                    setTheme(R.style.AppTheme_Light_Blue);
                    break;
                case "1":
                    setTheme(R.style.AppTheme_Light_Cyan);
                    break;
                case "2":
                    setTheme(R.style.AppTheme_Light_Gray);
                    break;
                case "3":
                    setTheme(R.style.AppTheme_Light_Green);
                    break;
                case "4":
                    setTheme(R.style.AppTheme_Light_Purple);
                    break;
                case "5":
                    setTheme(R.style.AppTheme_Light_Red);
                    break;
            }
        setContentView(R.layout.activity_main);
        displayPrimary = (TextView) findViewById(R.id.display_primary);
        displaySecondary = (TextView) findViewById(R.id.display_secondary);
        hsv = (HorizontalScrollView) findViewById(R.id.display_hsv);
        TextView[] digits = {
                (TextView) findViewById(R.id.button_0),
                (TextView) findViewById(R.id.button_1),
                (TextView) findViewById(R.id.button_2),
                (TextView) findViewById(R.id.button_3),
                (TextView) findViewById(R.id.button_4),
                (TextView) findViewById(R.id.button_5),
                (TextView) findViewById(R.id.button_6),
                (TextView) findViewById(R.id.button_7),
                (TextView) findViewById(R.id.button_8),
                (TextView) findViewById(R.id.button_9)};
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
                (TextView) findViewById(R.id.button_sin),
                (TextView) findViewById(R.id.button_cos),
                (TextView) findViewById(R.id.button_tan),
                (TextView) findViewById(R.id.button_ln),
                (TextView) findViewById(R.id.button_log),
                (TextView) findViewById(R.id.button_factorial),
                (TextView) findViewById(R.id.button_pi),
                (TextView) findViewById(R.id.button_e),
                (TextView) findViewById(R.id.button_exponent),
                (TextView) findViewById(R.id.button_start_parenthesis),
                (TextView) findViewById(R.id.button_end_parenthesis),
                (TextView) findViewById(R.id.button_square_root),
                (TextView) findViewById(R.id.button_add),
                (TextView) findViewById(R.id.button_subtract),
                (TextView) findViewById(R.id.button_multiply),
                (TextView) findViewById(R.id.button_divide),
                (TextView) findViewById(R.id.button_decimal),
                (TextView) findViewById(R.id.button_equals)};
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
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.delete();
            }
        });
        findViewById(R.id.button_delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!displayPrimary.getText().toString().trim().equals("")) {
                    final View displayOverlay = findViewById(R.id.display_overlay);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Animator circle = ViewAnimationUtils.createCircularReveal(
                                displayOverlay,
                                displayOverlay.getMeasuredWidth() / 2,
                                displayOverlay.getMeasuredHeight(),
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
        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        calculator = new Calculator(this);
        if (savedInstanceState != null)
            setText(savedInstanceState.getString("text"));
        if (sp.getInt("launch_count", 5) == 0) {
            RateDialog.show(this);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("launch_count", -1);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            setText(getText());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("text", getText());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setText(savedInstanceState.getString("text"));
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
