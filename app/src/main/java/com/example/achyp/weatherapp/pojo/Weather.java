package com.example.achyp.weatherapp.pojo;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    public int weatherId;
    public String main;
    public String description;
    public String icon;
}
