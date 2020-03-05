package com.yami.studio.banana.merchantapp.activity.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.network.ApiClient;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import java.util.HashMap;

public class MainViewModel extends AndroidViewModel {

    private ApiClient client;
    private String url;

    private HashMap<String, String> header = new HashMap<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        url = BuildConfig.URL + BuildConfig.PRODUCTS;

        header.put("Content-type", "application/json");
    }

    LiveData<NetworkStatus> getProductData(){
        return client.call(Request.Method.GET, url, null, header, null);
    }
}
