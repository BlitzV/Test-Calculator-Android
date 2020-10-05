package com.test.chinchin.testcalculator.root;

import android.app.Application;
import android.content.Intent;

import com.test.chinchin.testcalculator.activities.CalculatorActivity;
import com.test.chinchin.testcalculator.retrofit.RetrofitModule;
import com.test.chinchin.testcalculator.login.LoginActivity;
import com.test.chinchin.testcalculator.preferences.PreferencesHelper;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.SAVE_USER;

//base class in the app, build of dagger injector to all app and
//launch activities if logged in
public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule())
                .build();


        if(PreferencesHelper.GetStringValue(this,SAVE_USER)!=null){
            Intent intent = new Intent(App.this, CalculatorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(App.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public static AppComponent getAppComponent() {
        return component;
    }

}
