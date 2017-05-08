package com.example.achyp.weatherapp.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achyp.weatherapp.R;
import com.example.achyp.weatherapp.WeatherApplication;
import com.example.achyp.weatherapp.callback.Observer;
import com.example.achyp.weatherapp.pojo.Forecast;
import com.example.achyp.weatherapp.services.WeatherService;
import com.example.achyp.weatherapp.type.ValueType;
import com.example.achyp.weatherapp.utils.StringUtils;

import javax.inject.Inject;

/**
 * Class for presentation main information.
 */
public class MainActivity extends AppCompatActivity implements Observer.OnReceiveResponseListener {
    private static final String TAG = "MainActivity";
    private static final String FORECAST_KEY = "forecast";

    @Inject
    WeatherService mWeatherService;

    @Inject
    Observer mObserver;

    // UI component
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private TextView mCityTextView;
    private TextView mTempTextView;
    private TextView mRainTextView;
    private TextView mWindTextView;

    // Model
    private Forecast mForecast;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mObserver.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mObserver.unregister(this);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putSerializable(FORECAST_KEY, mForecast);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(FORECAST_KEY)) {
            mForecast = (Forecast) savedInstanceState.getSerializable(FORECAST_KEY);
            updateView(mForecast);
        }
    }

    private void inject() {
        ((WeatherApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        initSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
    }

    private void initSearchView(final Menu menu) {
        final SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem menuItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                mWeatherService.requestWeatherByCityName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                return false;
            }
        });
    }

    @Override
    public void onSuccess(final Forecast forecast) {
        mForecast = forecast;
        updateView(forecast);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "City wasn't found", Toast.LENGTH_LONG).show();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar);
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mCityTextView = (TextView) findViewById(R.id.collapse_city_text);
        mTempTextView = (TextView) findViewById(R.id.collapse_temp_text);
        mRainTextView = (TextView) findViewById(R.id.collapse_rain_text);
        mWindTextView = (TextView) findViewById(R.id.collapse_wind_text);
    }

    private void updateView(final Forecast forecast) {
        findViewById(R.id.collapse_forecast_root_view).setVisibility(View.VISIBLE);
        mCityTextView.setText(forecast.name);
        mTempTextView.setText(StringUtils.convertFahrenheitToCelsius(forecast.main.temperature));
        if (forecast.rain == null) {
            mRainTextView.setText(getString(R.string.main_activity_no_rain_text));
        } else {
            mRainTextView.setText(StringUtils.appendToText(ValueType.RAIN, forecast.rain.rain));

        }
        mWindTextView.setText(StringUtils.appendToText(ValueType.WIND, forecast.wind.speed));
    }
}