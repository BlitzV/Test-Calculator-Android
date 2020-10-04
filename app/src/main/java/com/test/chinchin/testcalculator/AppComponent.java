package com.test.chinchin.testcalculator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        RetrofitModule.class})
public interface AppComponent {

    void inject (MainActivity mainActivity);
}
