package com.example.achyp.weatherapp.serviceimpl;

import android.util.Log;

import com.example.achyp.weatherapp.BuildConfig;
import com.example.achyp.weatherapp.pojo.Forecast;
import com.example.achyp.weatherapp.services.WeatherService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherServiceImpl implements WeatherService {
    private static final String TAG = "WeatherServiceImpl";

    private Retrofit mRetrofit;
    private WeatherAPI mWeatherAPI;


    interface WeatherAPI {
        @GET("weather")
        Call<Forecast> getForecast(@Query("q") String cityName, @Query("APPID") String apiKey);
    }

    public WeatherServiceImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
        mWeatherAPI = mRetrofit.create(WeatherAPI.class);
    }

    @Override
    public void requestWeatherByCityName(final String cityName) {
        final Call<Forecast> call = mWeatherAPI.getForecast(cityName, BuildConfig.API_KEY);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(final Call<Forecast> call, final Response<Forecast> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(final Call<Forecast> call, final Throwable t) {
            }
        });
    }
}
