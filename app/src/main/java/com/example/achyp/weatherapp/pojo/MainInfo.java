package com.example.achyp.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by achyp on 2/18/17.
 */

public class MainInfo {
    @SerializedName("temp")
    public float temperature;
    public int pressure;
    public int humidity;
    public float temp_min;
    public float temp_max;
}
