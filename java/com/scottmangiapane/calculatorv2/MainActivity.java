package com.scottmangiapane.calculatorv2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CalculatorView calculatorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp != null && sp.getBoolean("pref_dark", false))
            setTheme(R.style.AppTheme_Dark_Blue);
        else
            setTheme(R.style.AppTheme_Light_Blue);
        setContentView(R.layout.activity_main);
        calculatorView = new CalculatorView(this);
        if (savedInstanceState != null)
            calculatorView.setText(savedInstanceState.getString("text"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("text", calculatorView.getText());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorView.setText(savedInstanceState.getString("text"));
    }
}
