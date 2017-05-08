package com.example.achyp.weatherapp.utils;


import android.support.annotation.NonNull;

import com.example.achyp.weatherapp.type.ValueType;

/**
 * Class for
 */
public final class StringUtils {
    private final static String RAIN_STANDARD = "mm";
    private final static String WIND_STANDARD = "m/s";
    private final static String CELSIUS = "°";

    private StringUtils() {
    }

    public static String appendToText(@NonNull final ValueType type, @NonNull final Number text) {
        switch (type) {
            case RAIN:
                return text + RAIN_STANDARD;
            case WIND:
                return text + WIND_STANDARD;
            default:
                return null;
        }
    }

    public static String convertFahrenheitToCelsius(@NonNull final float temperature) {
        final int celsius = (int) (temperature - 32) * 5 / 9;
        return celsius + CELSIUS;
    }
}
