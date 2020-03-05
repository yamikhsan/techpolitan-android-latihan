package com.yami.studio.banana.merchantapp.activity.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.network.ApiClient;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import java.util.HashMap;

public class DetailViewModel extends AndroidViewModel {

    private ApiClient client;
    private String baseUrl;
    private HashMap<String, String> header = new HashMap<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        baseUrl = BuildConfig.URL;

        header.put("Content-type", "application/json");
    }

    LiveData<NetworkStatus> getProduct(long id){
        String url = baseUrl + BuildConfig.ID + id;
        return client.call(Request.Method.GET, url, null, header, null);
    }

    LiveData<NetworkStatus> delete(long id){
        String url = baseUrl + BuildConfig.PRODUCT + id + BuildConfig.DELETE;
        return client.call(Request.Method.DELETE, url, null, header, null);
    }

}
