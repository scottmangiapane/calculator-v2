package com.scottmangiapane.calculatorv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private CalculatorView calculatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        if (sp != null && sp.getBoolean("pref_dark", false))
            Log.w("########", "true");
            // setTheme(R.style.AppTheme_Dark);
        else
            Log.w("########", "false");
            // setTheme(R.style.AppTheme_Light);
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
