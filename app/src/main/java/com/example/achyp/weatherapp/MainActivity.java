package com.example.achyp.weatherapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.achyp.weatherapp.services.WeatherService;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    WeatherService mWeatherService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();
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
        switch (item.getItemId()) {
            case R.id.search:
                // TODO start searching
                return true;
            default:
               return false;
        }
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
}
