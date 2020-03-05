package com.yami.studio.banana.merchantapp.network;

import com.android.volley.VolleyError;

public class NetworkStatus {

    private boolean status;
    private String response;
    private VolleyError volleyError;

    NetworkStatus(boolean status, String response) {
        this.status = status;
        this.response = response;
    }

    public NetworkStatus(boolean status) {
        this.status = status;
    }

    public VolleyError getVolleyError() {
        return volleyError;
    }

    void setVolleyError(VolleyError volleyError) {
        this.volleyError = volleyError;
    }

    void setResponse(String response) {
        this.response = response;
    }

    public boolean isStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
