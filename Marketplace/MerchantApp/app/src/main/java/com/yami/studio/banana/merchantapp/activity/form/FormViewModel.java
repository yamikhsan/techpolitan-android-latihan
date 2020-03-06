package com.yami.studio.banana.merchantapp.activity.form;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.network.ApiClient;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import org.json.JSONObject;

import java.util.HashMap;

public class FormViewModel extends AndroidViewModel {

    private ApiClient client;
    private String baseUrl;
    private HashMap<String, String> header = new HashMap<>();

    public FormViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        baseUrl = BuildConfig.URL;

        header.put("Content-type", "application/json");
    }

    LiveData<NetworkStatus> get(){
        String url = baseUrl + BuildConfig.CATEGORIES;
        return client.jsonRequest(Request.Method.GET, url, null, header);
    }

    LiveData<NetworkStatus> get(long id){
        String url = baseUrl + BuildConfig.ID + id;
        return client.jsonRequest(Request.Method.GET, url, null, header);
    }

    LiveData<NetworkStatus> post(int method, JSONObject json, String url){
        return client.jsonRequest(method, url, json, header);
    }

}
