package com.charliechao.fishintel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private SharedPreferences mSharedPrefs;

    private RadioGroup mTemperature;
    private RadioGroup mMetric;

    public SettingsFragment() {}

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefs = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        // Setup temperature unit switch
        mTemperature = (RadioGroup) v.findViewById(R.id.settings_radio_temperature_unit);
        if (mSharedPrefs.getString(Constants.PREF_KEY_TEMPERATURE, Constants.PREF_VALUE_TEMPERATURE_CELSIUS).equals(Constants.PREF_VALUE_TEMPERATURE_CELSIUS)) {
            mTemperature.check(R.id.settings_radio_temperature_celsius);
        } else {
            mTemperature.check(R.id.settings_radio_temperature_fahrenheit);
        }
        mTemperature.setOnCheckedChangeListener(this);
        // Setup metric unit switch
        mMetric = (RadioGroup) v.findViewById(R.id.settings_radio_metric_unit);
        if (mSharedPrefs.getString(Constants.PREF_KEY_METRIC, Constants.PREF_VALUE_METRIC_METRIC).equals(Constants.PREF_VALUE_METRIC_METRIC)) {
            mMetric.check(R.id.settings_radio_metric_metric);
        } else {
            mMetric.check(R.id.settings_radio_metric_imperial);
        }
        mMetric.setOnCheckedChangeListener(this);
        // Change app title text
        TextView txtAbout = (TextView) v.findViewById(R.id.settings_text_app_name);
        txtAbout.setText(getResources().getString(R.string.app_name) + " v" + BuildConfig.VERSION_NAME);
        return v;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if (radioGroup == mTemperature) {
            if (i == R.id.settings_radio_temperature_fahrenheit) {
                mSharedPrefs.edit().putString(Constants.PREF_KEY_TEMPERATURE, Constants.PREF_VALUE_TEMPERATURE_FAHRENHEIT).apply();
            } else {
                mSharedPrefs.edit().putString(Constants.PREF_KEY_TEMPERATURE, Constants.PREF_VALUE_TEMPERATURE_CELSIUS).apply();
            }
        }
        if (radioGroup == mMetric) {
            if (i == R.id.settings_radio_metric_imperial) {
                mSharedPrefs.edit().putString(Constants.PREF_KEY_METRIC, Constants.PREF_VALUE_METRIC_IMPERIAL).apply();
            } else {
                mSharedPrefs.edit().putString(Constants.PREF_KEY_METRIC, Constants.PREF_VALUE_METRIC_METRIC).apply();
            }
        }
    }

}
