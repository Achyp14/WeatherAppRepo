package com.example.achyp.weatherapp.callback;


import com.example.achyp.weatherapp.pojo.Forecast;

public interface Observer {
    void register(OnReceiveResponseListener listener);
    void unregister(OnReceiveResponseListener listener);

    void successResponse(Forecast forecast);

    void failedResponse();

    interface OnReceiveResponseListener {
        void onSuccess(Forecast forecast);

        void onFailure();
    }
}
