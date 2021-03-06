package com.yami.studio.banana.merchantapp.activity.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.network.ApiClient;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import java.util.HashMap;

public class UserViewModel extends AndroidViewModel {

    private ApiClient client;
    private HashMap<String, String> header = new HashMap<>();
    private String url;

    public UserViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);

        url = BuildConfig.URL + BuildConfig.GETUSER;
        header.put("Accept", "application/json");
    }

    LiveData<NetworkStatus> get(String token){
        header.put("Authorization", token);
        return client.jsonRequest(Request.Method.GET, url, null, header);
    }
}
