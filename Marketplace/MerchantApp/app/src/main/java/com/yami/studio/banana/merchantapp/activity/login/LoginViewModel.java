package com.yami.studio.banana.merchantapp.activity.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.yami.studio.banana.merchantapp.BuildConfig;
import com.yami.studio.banana.merchantapp.network.ApiClient;
import com.yami.studio.banana.merchantapp.network.NetworkStatus;

import java.util.HashMap;

public class LoginViewModel extends AndroidViewModel {

    private ApiClient client;
    private HashMap<String, String> header;
    private String url;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        url = BuildConfig.URL + BuildConfig.SIGNIN;
        header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("Accept", "application/json");
    }

    public LiveData<NetworkStatus> get(HashMap<String, String> params){
        return client.parmsRequest(Request.Method.POST, url, params, header);
    }
}
