package com.example.achyp.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;


public class MainInfo {
    @SerializedName("temp")
    public float temperature;
    public float pressure;
    public float humidity;
    public float temp_min;
    public float temp_max;
}
