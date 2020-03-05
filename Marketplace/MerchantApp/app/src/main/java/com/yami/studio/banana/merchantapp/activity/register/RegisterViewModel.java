package com.yami.studio.banana.merchantapp.activity.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.network.ApiClient;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import java.util.HashMap;

public class RegisterViewModel extends AndroidViewModel {

    private String url;
    private ApiClient client;

    private HashMap<String, String> header = new HashMap<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        url = BuildConfig.URL + BuildConfig.SIGNUP;

        header.put("Content-type", "application/x-www-form-urlencoded");
        header.put("Accept", "application/json");
    }

    LiveData<NetworkStatus> post(HashMap<String, String> params){
        return client.call(Request.Method.POST, url, null, header, params);
    }

}
