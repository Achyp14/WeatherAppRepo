package com.example.achyp.weatherapp.callback;

import android.support.annotation.NonNull;

import com.example.achyp.weatherapp.pojo.Forecast;

import java.util.ArrayList;
import java.util.List;


public class ObserverImpl implements Observer {

    final List<OnReceiveResponseListener> mListeners;

    public ObserverImpl() {
        mListeners = new ArrayList<>();
    }

    @Override
    public void register(@NonNull final OnReceiveResponseListener activity) {
        mListeners.add(activity);
    }

    @Override
    public void unregister(@NonNull final OnReceiveResponseListener activity) {
        mListeners.remove(activity);
    }

    @Override
    public void successResponse(final Forecast forecast) {
        for (final OnReceiveResponseListener listener : mListeners) {
            listener.onSuccess(forecast);
        }
    }

    @Override
    public void failedResponse() {
        for (final OnReceiveResponseListener listener : mListeners) {
            listener.onFailure();
        }
    }
}
