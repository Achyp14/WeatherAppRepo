package com.example.achyp.weatherapp.manager;

import com.example.achyp.weatherapp.dbmodel.City;

import java.util.List;


public interface DBManager {
    void addCity(String cityName);
    List<City> getAllCities();
    City getCityByName(String cityName);

    void setListener(DBManagerImpl.OnDbChangeListener listener);
}
