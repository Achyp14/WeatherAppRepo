package com.example.achyp.weatherapp.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achyp.weatherapp.R;
import com.example.achyp.weatherapp.application.WeatherApplication;
import com.example.achyp.weatherapp.adapters.CityAdapter;
import com.example.achyp.weatherapp.callback.Observer;
import com.example.achyp.weatherapp.dbmodel.City;
import com.example.achyp.weatherapp.manager.DBManager;
import com.example.achyp.weatherapp.manager.DBManagerImpl;
import com.example.achyp.weatherapp.pojo.Forecast;
import com.example.achyp.weatherapp.services.WeatherService;
import com.example.achyp.weatherapp.type.ValueType;
import com.example.achyp.weatherapp.utils.StringUtils;
import com.example.achyp.weatherapp.utils.UIUtils;


import javax.inject.Inject;

/**
 * Class for presentation main information.
 */
public class MainActivity extends AppCompatActivity implements Observer.OnReceiveResponseListener, DBManagerImpl.OnDbChangeListener {
    private static final String TAG = "MainActivity";
    private static final String FORECAST_KEY = "forecast";

    @Inject
    WeatherService mWeatherService;

    @Inject
    Observer mObserver;

    @Inject
    DBManager mDbManager;

    // UI component
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private TextView mCityTextView;
    private TextView mTempTextView;
    private TextView mRainTextView;
    private TextView mWindTextView;
    private FloatingActionButton mFloatingActionButton;
    private ListView mCityListView;

    // Model
    private Forecast mForecast;
    private CityAdapter mCityAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
        initView();
        mDbManager.setListener(this);
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
                UIUtils.hideKeyboard(MainActivity.this);
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
        Toast.makeText(this, R.string.city_wan_not_found_text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdd(final City city) {
        mCityAdapter.addCity(city);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_root);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mCityTextView = (TextView) findViewById(R.id.collapse_city_text);
        mTempTextView = (TextView) findViewById(R.id.collapse_temp_text);
        mRainTextView = (TextView) findViewById(R.id.collapse_rain_text);
        mWindTextView = (TextView) findViewById(R.id.collapse_wind_text);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.collapse_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mCityTextView != null && !TextUtils.isEmpty(mCityTextView.getText().toString())) {
                    mDbManager.addCity(mCityTextView.getText().toString());
                }
            }
        });
        initCityListView();
    }

    private void updateView(final Forecast forecast) {
        if (forecast != null) {
            findViewById(R.id.collapse_forecast_root_view).setVisibility(View.VISIBLE);
            mCityTextView.setText(forecast.name);
            mTempTextView.setText(StringUtils.convertKelvinToCelsius(forecast.main.temperature));
            if (forecast.rain == null) {
                mRainTextView.setText(getString(R.string.main_activity_no_rain_text));
            } else {
                mRainTextView.setText(StringUtils.appendToText(ValueType.RAIN, forecast.rain.rain));

            }
            mWindTextView.setText(StringUtils.appendToText(ValueType.WIND, forecast.wind.speed));
        }
    }


    private void initCityListView() {
        mCityListView = (ListView) findViewById(R.id.activity_main_recycle_view);
        mCityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final City city = (City) parent.getItemAtPosition(position);
                mWeatherService.requestWeatherByCityName(city.getName());
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });
        if (mCityListView != null) {
            mCityAdapter = new CityAdapter(this, mDbManager.getAllCities());
            mCityListView.setAdapter(mCityAdapter);
        }
    }

}