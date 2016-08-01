package com.scottmangiapane.calculatorv2;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class OnAppStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("launch_count", sp.getInt("launch_count", 0) + 1);
        editor.apply();
    }
}
