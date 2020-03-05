package com.yami.studio.banana.customerapp.network;

public class NetworkStatus {

    private boolean status;
    private String response;

    public NetworkStatus(boolean status, String response) {
        this.status = status;
        this.response = response;
    }

    public boolean isStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
