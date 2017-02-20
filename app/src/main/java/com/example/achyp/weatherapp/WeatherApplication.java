package com.example.achyp.weatherapp;

import android.support.multidex.MultiDexApplication;

import com.example.achyp.weatherapp.dagger.AppComponent;
import com.example.achyp.weatherapp.dagger.AppModule;
import com.example.achyp.weatherapp.dagger.DaggerAppComponent;


public class WeatherApplication extends MultiDexApplication {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(BASE_URL)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
