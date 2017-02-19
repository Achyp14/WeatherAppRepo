package com.example.achyp.weatherapp.dagger;

import com.example.achyp.weatherapp.serviceimpl.WeatherServiceImpl;
import com.example.achyp.weatherapp.services.WeatherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {
    private String baseURl;

    public AppModule(final String baseURl) {
        this.baseURl = baseURl;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(final Gson gson) {
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseURl)
                .build();
    }

    @Provides
    @Singleton
    WeatherService provideWeatherService(final Retrofit retrofit) {
        return new WeatherServiceImpl(retrofit);
    }
}
