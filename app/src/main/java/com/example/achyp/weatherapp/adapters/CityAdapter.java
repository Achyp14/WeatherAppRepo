package com.example.achyp.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.achyp.weatherapp.R;
import com.example.achyp.weatherapp.dbmodel.City;

import java.util.List;

/**
 * Adapter for showing cities in drawer layout.
 */
public class CityAdapter extends BaseAdapter {
    private List<City> mCityList;
    private LayoutInflater mLayoutInflater;

    public CityAdapter(final Context context, final List<City> cityList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mCityList = cityList;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final TextView textView;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.city_item, parent, false);
            textView = (TextView) convertView.findViewById(R.id.city_name);
            convertView.setTag(textView);
        } else {
            textView = (TextView) convertView.getTag();
        }

        final City city = getItem(position);
        textView.setText(city.getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return mCityList.size();
    }

    @Override
    public City getItem(final int position) {
        return mCityList.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    public void addCity(final City city) {
        mCityList.add(city);
        notifyDataSetChanged();
    }
}

