package com.test.chinchin.testcalculator.preferences;

import android.content.Context;
import android.content.SharedPreferences;

//class to help with the preferences in the app
public class PreferencesHelper {

    private static final String PREFERENCES = "preferences";

    public static void SetStringValue(Context context, String key, String save) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, save);
        editor.apply();
    }

    public static void ClearPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

    public static String GetStringValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }
}
