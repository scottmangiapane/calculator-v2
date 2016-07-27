package com.scottmangiapane.calculatorv2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.TaskStackBuilder;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        findPreference("pref_theme").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                ListPreference lp = (ListPreference) findPreference("pref_theme");
                String url = getActivity().getPackageName();
                String previous = url + ".MainActivity-" + lp.getEntry();
                String next = url + ".MainActivity-" + lp.getEntries()[Integer.parseInt(o.toString())];
                getActivity().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(url, previous),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                getActivity().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(url, next),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                getActivity().finish();
                TaskStackBuilder.create(getActivity())
                        .addNextIntent(new Intent(getActivity(), MainActivity.class))
                        .addNextIntent(getActivity().getIntent())
                        .startActivities();
                return true;
            }
        });
        findPreference("pref_dark").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                getActivity().finish();
                TaskStackBuilder.create(getActivity())
                        .addNextIntent(new Intent(getActivity(), MainActivity.class))
                        .addNextIntent(getActivity().getIntent())
                        .startActivities();
                return true;
            }
        });
    }
}
