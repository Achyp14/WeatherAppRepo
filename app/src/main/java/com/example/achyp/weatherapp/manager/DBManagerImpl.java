package com.example.achyp.weatherapp.manager;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.activeandroid.query.Select;
import com.example.achyp.weatherapp.dbmodel.City;

import java.util.List;


/**
 * Class for managing sql operations.
 */
public class DBManagerImpl implements DBManager {
    private static final String TAG = "DBManagerImpl";


    private OnDbChangeListener mOnDbChangeListener;

    public DBManagerImpl() {
    }


    @Override
    public void addCity(final String cityName) {
        try {
            final City city = new City(cityName);
            city.save();
            mOnDbChangeListener.onAdd(city);
        } catch (final SQLiteConstraintException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public List<City> getAllCities() {
        return new Select().from(City.class).execute();
    }

    @Override
    public City getCityByName(final String cityName) {
        final List<City> cities = getAllCities();
        for (final City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    @Override
    public void setListener(final OnDbChangeListener listener) {
        mOnDbChangeListener = listener;
    }

    public interface OnDbChangeListener {
        void onAdd(City city);
    }
}
