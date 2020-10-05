package com.test.chinchin.testcalculator.root;

import com.test.chinchin.testcalculator.activities.CalculatorActivity;
import com.test.chinchin.testcalculator.retrofit.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

//Dagger component
@Singleton
@Component(modules = {AppModule.class,
        RetrofitModule.class})
public interface AppComponent {

    void inject (CalculatorActivity calculatorActivity);
}
