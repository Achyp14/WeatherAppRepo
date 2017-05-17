package com.example.achyp.weatherapp.pojo;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Base pojo class.
 */
public class Forecast implements Serializable{
    @SerializedName("coord")
    public Coordinates coordinates;
    public List<Weather> weather;
    public String base;
    public MainInfo main;
    public float visibility;
    public Wind wind;
    public Cloud cloud;
    public long dt;
    public Rain rain;
    public Snow snow;
    public AdditionalInfo sys;
    public int id;
    public String name;
    public int cod;
}
