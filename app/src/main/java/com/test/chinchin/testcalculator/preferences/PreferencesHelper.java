package com.test.chinchin.testcalculator.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferencesHelper {

    private static final String PREFERENCES = "preferences";

    public static void SetStringValue(Context context, String key, String save) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, save);
        editor.apply();
    }

    public static void SetIntValue(Context context, String key, Integer save) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, save);
        editor.apply();
    }

    public static void SetBooleanValue(Context context, String key, Boolean save) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, save);
        editor.apply();
    }

    public static String GetStringValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    public static Integer GetIntValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);
    }

    public static Boolean GetBooleanValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
