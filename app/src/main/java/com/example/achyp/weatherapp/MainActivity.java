package com.example.achyp.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.achyp.weatherapp.services.WeatherService;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    @Inject
    WeatherService weatherService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((WeatherApplication) getApplication()).getAppComponent().inject(this);
    }
}
