package com.scottmangiapane.calculatorv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private SettingsView settingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(R.style.AppTheme_Dark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsView = new SettingsView(this);
    }
}
