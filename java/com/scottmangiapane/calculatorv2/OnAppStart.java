package com.scottmangiapane.calculatorv2;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

public class OnAppStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("SCORE_DATA", Context.MODE_PRIVATE);
        if (sp.getInt("launch_count", 0) == 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Rate Calculator v2")
                    .setMessage("Would you like to rate Calculator v2 on the Google Play store?")
                    .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                            } catch (android.content.ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }
                    })
                    .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .create().show();
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("launch_count", sp.getInt("launch_count", 0) + 1);
        editor.apply();
    }
}
