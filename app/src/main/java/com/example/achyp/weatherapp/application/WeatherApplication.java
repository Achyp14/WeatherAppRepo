package com.example.achyp.weatherapp.application;

import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.achyp.weatherapp.dagger.AppComponent;
import com.example.achyp.weatherapp.dagger.AppModule;
import com.example.achyp.weatherapp.dagger.DaggerAppComponent;
import com.example.achyp.weatherapp.dbmodel.City;
import com.squareup.leakcanary.LeakCanary;


public class WeatherApplication extends MultiDexApplication {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(BASE_URL)).build();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        final Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("AA_DB_NAME.db")
                .setDatabaseVersion(1)
                .addModelClasses(City.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);
        LeakCanary.install(this); // detect memory leak in app
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
