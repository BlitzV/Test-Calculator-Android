package com.test.chinchin.testcalculator;

import android.app.Application;
import android.content.Intent;

import com.test.chinchin.testcalculator.login.LoginActivity;
import com.test.chinchin.testcalculator.preferences.PreferencesHelper;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.SAVE_USER;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(PreferencesHelper.GetStringValue(this,SAVE_USER)!=null){
            Intent intent = new Intent(App.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(App.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
