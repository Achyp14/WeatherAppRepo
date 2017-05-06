package com.example.achyp.weatherapp.dagger;


import com.example.achyp.weatherapp.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject (MainActivity activity);
}
