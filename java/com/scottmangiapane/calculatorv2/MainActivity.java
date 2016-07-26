package com.scottmangiapane.calculatorv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CalculatorView calculatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(R.style.AppTheme_Dark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculatorView = new CalculatorView(this);
        if (savedInstanceState != null)
            calculatorView.setText(savedInstanceState.getString("text"));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("text", calculatorView.getText());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorView.setText(savedInstanceState.getString("text"));
    }
}
